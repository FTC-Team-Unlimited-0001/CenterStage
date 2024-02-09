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
public class microtest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        centerStageMachine robot = new centerStageMachine(hardwareMap);
        ElapsedTime timer = new ElapsedTime();
        telemetry.addData("status", "initalized");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            //PID and telemetry
            telemetry.addData("microOnePos", robot.microOne.getPosition());
            telemetry.update();
            if(gamepad2.a){
                robot.microOne.setPosition(1);
            }
            if(gamepad2.b){
                robot.microOne.setPosition(0);
            }
        }
    }
}