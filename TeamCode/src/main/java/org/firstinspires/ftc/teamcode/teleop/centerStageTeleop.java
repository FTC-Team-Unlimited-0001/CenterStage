package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.util.Machine;
import org.firstinspires.ftc.teamcode.util.PIDConstants;

@Config
@TeleOp
public class centerStageTeleop extends LinearOpMode {
    double integralSum = 0;
    double Kp = PIDConstants.Kp;
    double Ki = PIDConstants.Ki;
    double Kd = PIDConstants.Kd;

    ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;

    private BNO055IMU imu;

    private final FtcDashboard dashboard = FtcDashboard.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {

        Machine robot = new Machine(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu.initialize(parameters);

        double refrenceAngle = Math.toRadians(90);
        waitForStart();

        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            telemetry.addData("microOnePos", robot.microOne.getPosition());
            telemetry.addData("microOneInfo", robot.microOne.getConnectionInfo());
            telemetry.update();
            double microPos = 0;
            double servoPower = 0;
            double driveSpeed = 1;
            if (gamepad1.right_bumper) {
                driveSpeed = 2;
            } else if (gamepad1.left_bumper){
                driveSpeed = 0.5;
            } else {
                driveSpeed = 1;
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

            robot.frontLeft.setPower(frontLeftPower / driveSpeed);
            robot.backLeft.setPower(backLeftPower / driveSpeed);
            robot.frontRight.setPower(frontRightPower / driveSpeed);
            robot.backRight.setPower(backRightPower / driveSpeed);

            if (gamepad2.a) {
                robot.intakeServo.setDirection(CRServo.Direction.REVERSE);
                robot.higherIntakeServo.setDirection(CRServo.Direction.FORWARD);
                robot.thirdIntakeServo.setDirection(CRServo.Direction.FORWARD);
                servoPower = 0.85;

            }else if(gamepad2.b) {
                robot.intakeServo.setDirection(CRServo.Direction.FORWARD);
                robot.higherIntakeServo.setDirection(CRServo.Direction.REVERSE);
                robot.thirdIntakeServo.setDirection(CRServo.Direction.REVERSE);
                servoPower = 0.85;
            }
            robot.intakeServo.setPower(servoPower);
            robot.higherIntakeServo.setPower(servoPower);
            robot.thirdIntakeServo.setPower(servoPower);

            if (gamepad2.y){
                robot.microOne.setPosition(0.5);
                robot.microTwo.setPosition(0.5);

            }
        }
    }
    public double PIDControl(double refrence, double state) {
        double error = angleWrap(refrence - state);
        telemetry.addData("Error: ", error);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);
        return output;
    }
    public double angleWrap(double radians){
        while(radians > Math.PI){
            radians -= 2 * Math.PI;
        }
        while(radians < -Math.PI){
            radians += 2 * Math.PI;
        }
        return radians;
    }
}