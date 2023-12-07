package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;


public class Machine {


        // DT motors
        public DcMotor frontRight;

        public DcMotor frontLeft;
        public DcMotor backRight;
        public DcMotor backLeft;
        public CRServo intakeServo;
        HardwareMap hardwareMap;

        public Machine(HardwareMap hwMap){
            initialize(hwMap);
        }
        private void initialize(HardwareMap hwMap) {
            hardwareMap = hwMap;

            //Connect Motors
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            intakeServo = hardwareMap.get(CRServo.class, "intakeServo");

            //Set motor direction
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            frontLeft.setDirection(DcMotor.Direction.REVERSE);
            backRight.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            // Drivetrain motors
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }
}
