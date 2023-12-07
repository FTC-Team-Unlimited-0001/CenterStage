package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Machine;

@Config
@TeleOp
public class centerStageTeleop extends LinearOpMode {

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {

        Machine robot = new Machine(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        double mode = 1;
        waitForStart();

        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                mode = 2;
            } else {
                mode = 1;
            }
            if (gamepad1.left_bumper) {
                mode = 0.5;
            } else {
                mode = 1;
            }

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeft.setPower(frontLeftPower / mode);
            robot.backLeft.setPower(backLeftPower / mode);
            robot.frontRight.setPower(frontRightPower / mode);
            robot.backRight.setPower(backRightPower / mode);

            if (gamepad1.a){
                robot.intakeServo.setPower(1);
            } else {
                robot.intakeServo.setPower(0);
            }
        }
    }
}
