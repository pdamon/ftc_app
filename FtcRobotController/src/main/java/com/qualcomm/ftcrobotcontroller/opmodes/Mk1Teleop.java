package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Mk1Teleop extends LinearOpMode
{
    static final float threshold = 0.1f;
    
    DcMotor left_drive;
    DcMotor right_drive;
    DcMotor shoulder;
    DcMotor elbow;
    
    Servo[] hand = new Servo[2];
    float[] hand_positions = new float[]{0.0f, 0.0f};
    
    public Mk1Teleop() {}
    
    void rotateContinuousHandServo(float omega, int servo, float dt)
    {
        omega = Range.clip(omega, -1.0f, 1.0f);
        hand[servo].setPosition(omega);
    }
    
    void rotateHandServo(float omega, int servo, float dt)
    {
        hand_positions[servo] += omega*dt;
        hand_positions[servo] = Range.clip(hand_positions[servo], -1.0f, 1.0f);
        hand[servo].setPosition(hand_positions[servo]);
    }
    
    float deadZone(float val)
    {
        if(Math.abs(val) < threshold)
        {
            val = 0.0f;
        }
        else
        {
            val = val-(val/Math.abs(val))*threshold;
            val /= 1.0f-threshold;
        }
        return val;
    }
    
    @Override public void runOpMode()
        throws InterruptedException
    {
        left_drive  = hardwareMap.dcMotor.get("motor_1");
        right_drive = hardwareMap.dcMotor.get("motor_2");
        shoulder    = hardwareMap.dcMotor.get("motor_3");
        elbow       = hardwareMap.dcMotor.get("motor_4");

        hand[0] = hardwareMap.servo.get("servo_1");
        hand[1] = hardwareMap.servo.get("servo_2");

        waitForStart();

        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        
        float dt;
        float new_time = 0;
        float old_time = 0;
        
        for(;;)
        {
            new_time = (float) timer.time();
            dt = new_time-old_time;
            old_time = new_time;
            
            //drive
            float stick_x = gamepad1.left_stick_x;
            float stick_y = -gamepad1.left_stick_y;
            float norm = (float) Math.sqrt(((stick_x)*(stick_x))+((stick_y)*(stick_y)));
            if(norm < threshold)
            {
                stick_x = 0;
                stick_y = 0;
            }
            else
            {
                stick_x /= norm;
                stick_y /= norm;
            
                norm = (norm-threshold)/(1-threshold);
                norm = (norm-threshold)/(1-threshold);

                stick_x *= norm;
                stick_y *= norm;
            }
            
            float left_power = stick_y-stick_x;
            float right_power = stick_y+stick_x;
            right_power = Range.clip(right_power, -1, 1);
            left_power = Range.clip(left_power, -1, 1);
            right_drive.setPower(right_power);
            left_drive.setPower(left_power);
        
            //arm
            float stick_s = -gamepad2.left_stick_y;
            float stick_e = -gamepad2.left_stick_y;
            float s_power = deadZone(stick_s);
            float e_power = deadZone(stick_e);

            s_power = Range.clip(s_power, -1.0f, 1.0f);
            e_power = Range.clip(e_power, -1.0f, 1.0f);

            e_power *= 0.5;
            shoulder.setPower(s_power);
            elbow.setPower(e_power);

            //hand
            float stick_h1 = -gamepad2.right_stick_x;
            float stick_h2 = -gamepad2.right_stick_y;
            float h1_power = deadZone(stick_h1);
            float h2_power = deadZone(stick_h2);

            rotateHandServo(h1_power, 0, dt);
            rotateHandServo(h2_power, 1, dt);
            
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("delta t", String.format("%.2f", dt) + "ms");
            telemetry.addData("Shoulder stick", "shoulder val:" + String.format("%.2f", gamepad1.left_stick_y));
            telemetry.addData("elbow power", "elbow pwr: " + String.format("%.2f", -gamepad1.right_stick_y));
            
            waitOneFullHardwareCycle();
        }
    }
}
