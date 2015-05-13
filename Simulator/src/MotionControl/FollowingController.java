package MotionControl;

import java.util.*;
import java.lang.IllegalArgumentException;

import Avatars.*;

public class FollowingController extends Controller
{
    Enemy lead;
    User target;
    public FollowingController(Enemy followingVehicle, User target)
    {	
    	this.lead=followingVehicle;
    	this.target=target;
    	//TODO: finish this up 
    }
    
    @Override
    public Control getControl() {
	double w_desired = 5;
	double[] desired_pos = lead.getPosition();
	double[] pose = this.target.getPosition();
	
	double delta_x = desired_pos[0] - pose[0];
	double delta_y = desired_pos[1] - pose[1];
	double theta_d = Math.atan(delta_y / delta_x);

	if (delta_x < 0) {
	    theta_d += Math.PI;
	}

	double gain = 5;

	theta_d = wrapAngle(theta_d);
	w_desired = wrapAngle(theta_d - pose[2]);

	w_desired *= gain;

	if (w_desired > Math.PI/4) {
	    w_desired = Math.PI/4;
	}
	if (w_desired < -Math.PI/4) {
	    w_desired = -Math.PI/4;
	}

	double d= Math.sqrt(delta_x * delta_x + delta_y * delta_y);
	double v_desired = d;
	if (v_desired > 10) {
	    v_desired = 10;
	}
	if (v_desired < 5) {
	    v_desired = 5;
	}

	Control c = new Control(w_desired, v_desired);
	return c;
    }

    
    public static double wrapAngle(double theta) {
	while (theta < -Math.PI) {
	    theta += 2 * Math.PI;
	}
	while (theta > Math.PI) {
	    theta -= 2 * Math.PI;
	}
	return theta;
    }

	   
}