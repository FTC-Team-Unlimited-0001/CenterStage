package org.firstinspires.ftc.teamcode.auton;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous
public class centerStageAuto extends LinearOpMode {
    public SampleMecanumDrive robot;
    public static double DISTANCE = 5;
    public static Pose2d START_POSE = new Pose2d();

    @Override
    public void runOpMode() {

        robot = new SampleMecanumDrive(hardwareMap);
        robot.setPoseEstimate(START_POSE);

        Trajectory mytrajectory = robot.trajectoryBuilder(START_POSE)
                .strafeRight(DISTANCE)
                .build();
        Trajectory mytrajectory2 = robot.trajectoryBuilder(START_POSE)
                .strafeLeft(DISTANCE)
                .build();
        Trajectory mytrajectory3 = robot.trajectoryBuilder(START_POSE)
                .back(DISTANCE)
                .build();
        Trajectory mytrajectory4 = robot.trajectoryBuilder(START_POSE)
                .forward(DISTANCE)
                .build();


        waitForStart();

        if (opModeIsActive() && !isStopRequested()) {
            robot.followTrajectory(mytrajectory2);
        }
    }
}