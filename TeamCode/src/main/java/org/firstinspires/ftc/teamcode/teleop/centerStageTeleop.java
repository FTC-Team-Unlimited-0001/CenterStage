package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.util.centerStageMachine;

@Config
@TeleOp
public class centerStageTeleop extends LinearOpMode {
    public static double ANGLER_POWER = 0;
    public static double SERVO_POS = 0;
    public static double PLANE_POS = 0;
    private PIDController controller;

    public static double p = 0, i=0, d=0;
    public static double f = 0.88;
    public static int target = -1800;
    private final double ticks = 700 / 180.0;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();
    @Override
    public void runOpMode() throws InterruptedException {
        centerStageMachine robot = new centerStageMachine(hardwareMap);
        PIDController controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        ElapsedTime timer = new ElapsedTime();
        telemetry.addData("status", "initalized");
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            robot.planeServo.setPosition(0);
            //PID and telemetry
            telemetry.addData("dispensePos", robot.dispense.getPosition());
            telemetry.addData("planePos", robot.planeServo.getPosition());
            controller.setPID(p, i, d);
            int armPos = robot.angler.getCurrentPosition();
            double pid = controller.calculate(armPos, target);
            double ff = Math.cos(Math.toRadians(target / ticks)) * f;
            double power = pid + ff;

            telemetry.addData("armPos", armPos);
            telemetry.addData("target", target);
            telemetry.update();


            //initalizing variables
            double servoPower = 0;
            robot.dispense.setPosition(SERVO_POS);

            //drive
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

            //slowmode fastmode normal
            if (gamepad1.left_bumper){
                robot.frontLeft.setPower(frontLeftPower/4);
                robot.backLeft.setPower(backLeftPower/4);
                robot.frontRight.setPower(frontRightPower/4);
                robot.backRight.setPower(backRightPower/4);
            } else{
                robot.frontLeft.setPower(frontLeftPower);
                robot.backLeft.setPower(backLeftPower);
                robot.frontRight.setPower(frontRightPower);
                robot.backRight.setPower(backRightPower);
            }

            if(gamepad2.dpad_up){
                robot.LinearLeft.setPower(1);
                robot.LinearRight.setPower(1);
            } else if(gamepad2.dpad_down){
                robot.LinearLeft.setPower(-0.3);
                robot.LinearRight.setPower(-0.3);
            } else{
                robot.LinearLeft.setPower(0);
                robot.LinearRight.setPower(0);
            }

            if (gamepad2.right_bumper){
                robot.dispense.setPosition(0.8);
                Thread.sleep(1000);
                robot.dispense.setPosition(0);
            }

            if(gamepad2.left_stick_button){
                robot.planeServo.setPosition(0.6);
                Thread.sleep(10);
                robot.planeServo.setPosition(0);
            }
        }
    }
}