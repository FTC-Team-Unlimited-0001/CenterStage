package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Util.centerStageMachine;

@Config
@TeleOp
public class centerStageTeleop extends LinearOpMode {
    public static double ANGLER_POWER = 0;
    public static double SERVO_POS = 0;
    public static double PLANE_POS = 0;
    private PIDController controller;

    public static double p = 0, i=0, d=0;
    public static double f = 0;
    public static int target = 0;
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
            telemetry.addData("microOnePos", robot.microOne.getPosition());
            telemetry.addData("test", robot.angler.getCurrentPosition());
            telemetry.addData("microDongPos", robot.dispense.getPosition());
            telemetry.addData("planePos", robot.planeServo.getPosition());
            telemetry.addData("slidePos", robot.LinearRight.getCurrentPosition());
            controller.setPID(p, i, d);
            int armPos = robot.angler.getCurrentPosition();
            double pid = controller.calculate(armPos, target);
            double ff = Math.cos(Math.toRadians(target / ticks)) * f;
            double power = pid + ff;
            robot.angler.setPower(power);

            telemetry.addData("armPos", armPos);
            telemetry.addData("target", target);
            telemetry.update();

            int slidePos = robot.LinearRight.getCurrentPosition();

            //initalizing variables
            double servoPower = 0;
            robot.angler.setPower(ANGLER_POWER);
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
                robot.frontLeft.setPower(frontLeftPower/2);
                robot.backLeft.setPower(backLeftPower/2);
                robot.frontRight.setPower(frontRightPower/2);
                robot.backRight.setPower(backRightPower/2);
            } else if (gamepad1.right_bumper){
                robot.frontLeft.setPower(frontLeftPower*2);
                robot.backLeft.setPower(backLeftPower*2);
                robot.frontRight.setPower(frontRightPower*2);
                robot.backRight.setPower(backRightPower*2);
            } else{
                robot.frontLeft.setPower(frontLeftPower);
                robot.backLeft.setPower(backLeftPower);
                robot.frontRight.setPower(frontRightPower);
                robot.backRight.setPower(backRightPower);
            }

            //intake
            if (gamepad2.a) {
                robot.intakeServo.setDirection(CRServo.Direction.REVERSE);
                robot.higherIntakeServo.setDirection(CRServo.Direction.FORWARD);
                robot.thirdIntakeServo.setDirection(CRServo.Direction.FORWARD);
                servoPower = 0.5;

            }else if(gamepad2.b) {
                robot.intakeServo.setDirection(CRServo.Direction.FORWARD);
                robot.higherIntakeServo.setDirection(CRServo.Direction.REVERSE);
                robot.thirdIntakeServo.setDirection(CRServo.Direction.REVERSE);
                servoPower = 0.5;
            }
            if(gamepad2.dpad_up){
                robot.LinearLeft.setPower(0.8);
                robot.LinearRight.setPower(0.8);
            } else{
                robot.LinearLeft.setPower(0);
                robot.LinearRight.setPower(0);
            }

            if(gamepad2.dpad_down){
                robot.LinearLeft.setPower(-0.4);
                robot.LinearRight.setPower(-0.4);
            }
            // else if(slidePos < ?) {
            //       robot.LinearLeft.setPower(0);
            //       robot.LinearRight.setPower(0);
            // }

             else{
                robot.LinearLeft.setPower(0);
                robot.LinearRight.setPower(0);
            }


            if(gamepad2.right_bumper){
                robot.planeServo.setPosition(0.6);
                Thread.sleep(3000);
                robot.planeServo.setPosition(0);
            }

            if(gamepad2.right_stick_button){
                robot.LinearLeft.setPower(-0.8);
                robot.LinearRight.setPower(-0.8);
            }else{
                robot.LinearLeft.setPower(0);
                robot.LinearRight.setPower(0);
            }

            if(gamepad2.dpad_left){
                robot.microOne.setPosition(50);
            }

            //intake power
            robot.intakeServo.setPower(servoPower);
            robot.higherIntakeServo.setPower(servoPower);
            robot.thirdIntakeServo.setPower(servoPower);
            }
        }
    }