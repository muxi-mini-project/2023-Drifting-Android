package android.bignerdranch.drifting.Camera;

/**
 * 当前准备进行创作的camera
 */
public class Camera_ready_create {
    static Camera_ready_create camera_ready_create;
    Camera_ mCamera_;
   static public Camera_ready_create getCamera_ready_create(){
        if(camera_ready_create == null){
          camera_ready_create = new Camera_ready_create();
        }
        return camera_ready_create;
    }
    public void setCamera(Camera_ camera){
    mCamera_ = camera;
    }
    public Camera_ getCamera(){
        return mCamera_;
    }
}
