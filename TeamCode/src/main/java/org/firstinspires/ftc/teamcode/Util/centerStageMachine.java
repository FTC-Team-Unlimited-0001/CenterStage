package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class centerStageMachine {


        // DT motors
        public DcMotor frontRight;

        public DcMotor frontLeft;
        public DcMotor backRight;
        public DcMotor backLeft;
        public DcMotorEx angler;
        public CRServo intakeServo;
        public CRServo higherIntakeServo;
        public CRServo thirdIntakeServo;
        public Servo microOne;
        public Servo microTwo;
        public Servo dispense;
        public Servo planeServo;

        HardwareMap hardwareMap;

        public centerStageMachine(HardwareMap hwMap){
            initialize(hwMap);
        }
        private void initialize(HardwareMap hwMap) {
            hardwareMap = hwMap;

            //Connect Motors
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            angler = hardwareMap.get(DcMotorEx.class, "angler");
            intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
            higherIntakeServo = hardwareMap.get(CRServo.class, "higherIntakeServo");
            thirdIntakeServo = hardwareMap.get(CRServo.class, "thirdIntakeServo");
            planeServo = hardwareMap.get(Servo.class, "planeServo");
            microOne = hardwareMap.get(Servo.class, "microOne");
            microTwo = hardwareMap.get(Servo.class, "microTwo");
            dispense = hardwareMap.get(Servo.class, "dispense");


            //Set motor direction
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            //Other Motor stuff
            angler.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            angler.setDirection(DcMotorSimple.Direction.FORWARD);

            // Drivetrain motors
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }
}
