package com.programmergabut.larikuy.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar
import com.programmergabut.larikuy.R
import com.programmergabut.larikuy.db.Run
import com.programmergabut.larikuy.other.Constants.ACTION_PAUSE_SERVICE
import com.programmergabut.larikuy.other.Constants.ACTION_START_OR_RESUME_SERVICE
import com.programmergabut.larikuy.other.Constants.ACTION_STOP_SERVICE
import com.programmergabut.larikuy.other.Constants.MAP_ZOOM
import com.programmergabut.larikuy.other.Constants.POLYLINE_COLOR
import com.programmergabut.larikuy.other.Constants.POLYLINE_WIDTH
import com.programmergabut.larikuy.other.TrackingUtility
import com.programmergabut.larikuy.other.showSnackbar
import com.programmergabut.larikuy.services.Polyline
import com.programmergabut.larikuy.services.TrackingService
import com.programmergabut.larikuy.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.*
import javax.inject.Inject
import kotlin.math.round

const val CANCEL_TRACKING_DIALOG_TAG = "CANCEL_TRACKING_DIALOG_TAG"
const val SUCCESS = "SUCCESS"

@AndroidEntryPoint
class TrackingFragment(
    var viewModel: MainViewModel? = null
): Fragment(R.layout.fragment_tracking) {

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null
    private var curTimeInMillis = 0L

    private var menu: Menu? = null

    @set:Inject
    var weight = 55f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        btnToggleRun.setOnClickListener {
            toggleRun()
        }

        /*cancel Tracking Dialog */
        if(savedInstanceState != null){
            val cancelTrackingDialog = parentFragmentManager.findFragmentByTag(
                CANCEL_TRACKING_DIALOG_TAG) as CancelTrackingDialog?

            cancelTrackingDialog?.setYesListener {
                stopRun()
            }
        }

        mapView.getMapAsync {
            map = it
            addAllPolyline()
        }

        btnFinishRun.setOnClickListener {
            val msg = zoomToSeeWholeTrack()
            if (msg != SUCCESS) requireView().showSnackbar(msg, Snackbar.LENGTH_SHORT)

            endRunAndSaveToDB()
        }

        /*cancel Tracking Dialog */
        requireActivity().onBackPressedDispatcher.addCallback(
            object: OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    if(isTracking || (!isTracking && curTimeInMillis == 0L))
                        findNavController().popBackStack()

                    if(!isTracking && curTimeInMillis > 0L)
                        showCancelTrackingDialog()
                }
            }
        )

        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        TrackingService.isTracking.observe(viewLifecycleOwner, {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
            tvTimer.text = formattedTime
        })
    }

    private fun toggleRun(){
        if(isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        }
        else{
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu, menu)
        this.menu = menu
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        if(curTimeInMillis > 0L){
            this.menu?.getItem(0)?.isVisible = true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.miCancelTracking -> {
                showCancelTrackingDialog()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showCancelTrackingDialog(){
        CancelTrackingDialog().apply{
            setYesListener {
                stopRun()
            }
        }.show(parentFragmentManager, CANCEL_TRACKING_DIALOG_TAG)
    }

    private fun stopRun(){
        tvTimer.text = "00:00:00:00"
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(TrackingFragmentDirections.actionTrackingFragmentToRunFragment())
    }

    private fun updateTracking(isTracking: Boolean){
        this.isTracking = isTracking
        if(!isTracking && curTimeInMillis > 0L){
            btnToggleRun.text = "Start"
            btnFinishRun.visibility = View.VISIBLE
        }
        else if(isTracking){
            btnToggleRun.text = "Stop"
            menu?.getItem(0)?.isVisible = true
            btnFinishRun.visibility = View.GONE
        }
    }

    private fun zoomToSeeWholeTrack(): String {
        val bounds = LatLngBounds.Builder()

        try {

            for(polyline in pathPoints) {
                for(pos in polyline) {
                    bounds.include(pos)
                }
            }

            map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    mapView.width,
                    mapView.height,
                    (mapView.height * 0.05f).toInt()
                )
            )

            return SUCCESS
        }
        catch (ex: Exception){
            return ex.message.toString()
        }
    }

    private fun moveCameraToUser(){
        if(pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun endRunAndSaveToDB() {

        try {
            var distanceInMeters = 0
            for(polyline in pathPoints){
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }

            val avgSpeed = round((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val caloriesBurn = ((distanceInMeters / 1000f) * weight).toInt()

            if(avgSpeed.isInfinite()) requireView().showSnackbar("avgSpeed is infinite", Snackbar.LENGTH_SHORT)

            map?.snapshot {bmp ->
                val run = Run(bmp, dateTimeStamp, avgSpeed, distanceInMeters, curTimeInMillis, caloriesBurn)
                viewModel?.insertRun(run)

                Snackbar.make(requireActivity().findViewById(R.id.rootView), "Run save successfully", Snackbar.LENGTH_LONG).show()
                stopRun()
            }

        }
        catch (ex: Exception){
            requireView().showSnackbar(ex.message.toString(), Snackbar.LENGTH_SHORT)
        }

    }

    private fun addAllPolyline(){
        for(polyline in pathPoints){
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)

            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline(){
        if(pathPoints.isNotEmpty() && pathPoints.last().size > 1){
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2]
            val lastLatLng = pathPoints.last().last()
            val polylineOption = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)

            map?.addPolyline(polylineOption)
        }
    }


    private fun sendCommandToService(action: String){
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

}