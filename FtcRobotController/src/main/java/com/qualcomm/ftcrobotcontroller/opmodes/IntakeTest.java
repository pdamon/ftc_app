package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class IntakeTest extends OpMode
{
    DcMotor intake;
    public IntakeTest() {}

    @Override public void init()
    {
        intake = hardwareMap.dcMotor.get("motor_1");
    }


    @Override public void loop()
    {
        float stick_y = -gamepad1.left_stick_y;
        float intakePower = stick_y;

        //float norm = (float) Math.sqrt(((left_power)*(left_power))+((right_power)*(right_power)));
        if(Math.abs(stick_y) < 0.1)
        {
            intakePower = 0;
        }
        intakePower = Range.clip(intakePower, -1, 1);
        intake.setPower(intakePower);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Left stick", "left val:" + String.format("%.2f", gamepad1.left_stick_x));
        telemetry.addData("right power", "right pwr: " + String.format("%.2f", -gamepad1.left_stick_y));
    }

    @Override public void stop() {}
}