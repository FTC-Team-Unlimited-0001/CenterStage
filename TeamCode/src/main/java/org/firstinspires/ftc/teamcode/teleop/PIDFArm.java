package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.util.centerStageMachine;
@Config
@TeleOp
public class PIDFArm extends OpMode {
    centerStageMachine robot = new centerStageMachine(hardwareMap);
    private PIDController controller;

    public static double p = 0, i=0, d=0;
    public static double f = 0;
    public static int target = 0;
    private final double ticks = 700 / 180.0;

    @Override
    public void init() {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop(){
        controller.setPID(p, i, d);
        int armPos = robot.angler.getCurrentPosition();
        double pid = controller.calculate(armPos, target);
        double ff = Math.cos(Math.toRadians(target / ticks)) * f;
        double power = pid + ff;
        robot.angler.setPower(power);

        telemetry.addData("pos", armPos);
        telemetry.addData("target", target);
        telemetry.update();
    }
}
