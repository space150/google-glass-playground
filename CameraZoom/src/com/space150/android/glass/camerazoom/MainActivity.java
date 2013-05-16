package com.space150.android.glass.camerazoom;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener, Camera.OnZoomChangeListener
{
	public static String TAG = "CameraZoom";
	
	public static float FULL_DISTANCE = 8000.0f;
	
    private SurfaceView mPreview;
    private SurfaceHolder mPreviewHolder;
    private Camera mCamera;
    private boolean mInPreview = false;
    private boolean mCameraConfigured = false;
    private TextView mZoomLevelView;
    
    private GestureDetector mGestureDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPreview = (SurfaceView)findViewById(R.id.preview);
        mPreviewHolder = mPreview.getHolder();
        mPreviewHolder.addCallback(surfaceCallback);
        
        mZoomLevelView = (TextView)findViewById(R.id.zoomLevel);
        
        mGestureDetector = new GestureDetector(this, this);
    }

    @Override
    public void onResume() 
    {
        super.onResume();

        mCamera = Camera.open();
        startPreview();
    }

    @Override
    public void onPause() 
    {
        if ( mInPreview )
            mCamera.stopPreview();

        mCamera.release();
        mCamera = null;
        mInPreview = false;

        super.onPause();
    }

    private void initPreview(int width, int height) 
    {
        if ( mCamera != null && mPreviewHolder.getSurface() != null) {
            try 
            {
                mCamera.setPreviewDisplay(mPreviewHolder);
            }
            catch (Throwable t) 
            {
                Log.e(TAG, "Exception in initPreview()", t);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

            if ( !mCameraConfigured ) 
            {
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setPreviewSize(1920, 1080); // hard coded the largest size for now
                mCamera.setParameters(parameters);
                mCamera.setZoomChangeListener(this);
                
                mCameraConfigured = true;
            }
        }
    }

    private void startPreview() 
    {
        if ( mCameraConfigured && mCamera != null ) 
        {
            mCamera.startPreview();
            mInPreview = true;
        }
    }

    SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated( SurfaceHolder holder ) 
        {
        	// nothing
        }

        public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) 
        {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed( SurfaceHolder holder ) 
        {
            // nothing
        }
    };
    
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) 
    {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

	@Override
	public boolean onDown(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling( MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ) 
	{
		Camera.Parameters parameters = mCamera.getParameters();
		int zoom = parameters.getZoom();
		
		if ( velocityX < 0.0f )
		{
			zoom -= 10;
			if ( zoom < 0 )
				zoom = 0;
		}
		else if ( velocityX > 0.0f )
		{
			zoom += 10;
			if ( zoom > parameters.getMaxZoom() )
				zoom = parameters.getMaxZoom();
		}

		mCamera.startSmoothZoom(zoom);
		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		//Log.d(TAG, "distanceX: " + distanceX + ", distanceY: " + distanceY);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
		mZoomLevelView.setText("ZOOM: " + zoomValue);
		
	}
}