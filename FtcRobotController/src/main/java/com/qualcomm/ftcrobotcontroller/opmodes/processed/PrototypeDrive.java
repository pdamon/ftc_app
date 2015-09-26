package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;





public class PrototypeDrive extends OpMode
{
    DcMotor left_drive;
    DcMotor right_drive;

    public PrototypeDrive() {}

    @Override public void init()
    {
        left_drive = hardwareMap.dcMotor.get("motor_2");
        right_drive = hardwareMap.dcMotor.get("motor_1");
        left_drive.setDirection(DcMotor.Direction.REVERSE);
    }


    @Override public void loop()
    {
        float stick_x = gamepad1.left_stick_x;
        float stick_y = -gamepad1.left_stick_y;
        float left_power = stick_y+stick_x;
        float right_power = stick_y-stick_x;

        float norm = (float) Math.sqrt(((left_power)*(left_power))+((right_power)*(right_power)));
        if(norm < 0.1)
        {
            left_power = 0;
            right_power = 0;
        }
        right_drive.setPower(right_power);
        left_drive.setPower(left_power);

        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("left power", "left  pwr: " + String.format("%.2f", left_power));
        telemetry.addData("right power", "right pwr: " + String.format("%.2f", right_power));
    }

    @Override public void stop() {}
}
