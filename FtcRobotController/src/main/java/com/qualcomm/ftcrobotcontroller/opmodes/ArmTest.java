package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class ArmTest extends OpMode
{
    DcMotor shoulder;
    DcMotor elbow;
    public ArmTest() {}

    @Override public void init()
    {
        shoulder = hardwareMap.dcMotor.get("motor_1");
        elbow = hardwareMap.dcMotor.get("motor_2");
    }


    @Override public void loop()
    {
        float stick_s = -gamepad1.left_stick_y;
        float stick_e = -gamepad1.right_stick_y;
        float s_power = stick_s;
        float e_power = stick_e;

        //float norm = (float) Math.sqrt(((left_power)*(left_power))+((right_power)*(right_power)));
        float thresh = 0.1f;
        if(Math.abs(s_power) < thresh)
        {
            s_power = 0.0f;
        }
        else
        {
            s_power = s_power-s_power/Math.abs(s_power)*thresh;
            s_power /= 1.0f-thresh;
        }
        if(Math.abs(e_power) < thresh)
        {
            e_power = 0.0f;
        }
        else
        {
            e_power = e_power-e_power/Math.abs(e_power)*thresh;
            e_power /= 1.0f-thresh;
        }
        s_power = Range.clip(s_power, -1.0f, 1.0f);
        e_power = Range.clip(e_power, -1.0f, 1.0f);
        e_power *= 0.5;
        shoulder.setPower(s_power);
        elbow.setPower(e_power);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Shoulder stick", "shoulder val:" + String.format("%.2f", gamepad1.left_stick_y));
        telemetry.addData("elbow power", "elbow pwr: " + String.format("%.2f", -gamepad1.right_stick_y));
    }

    @Override public void stop() {}
}