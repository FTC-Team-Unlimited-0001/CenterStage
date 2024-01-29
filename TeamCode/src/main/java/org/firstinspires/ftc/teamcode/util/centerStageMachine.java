package org.firstinspires.ftc.teamcode.Util;

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

        public DcMotor LinearLeft;

        public DcMotor LinearRight;

<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/util/centerStageMachine.java
=======

>>>>>>> 2b08aeb0ca0ccf9a553a27c64e2acb6963fb8fd8:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Util/centerStageMachine.java
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
            LinearLeft = hardwareMap.get(DcMotor.class, "LinearLeft");
            LinearRight = hardwareMap.get(DcMotor.class, "LinearRight");


            //Set motor direction
            frontRight.setDirection(DcMotor.Direction.FORWARD);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.FORWARD);
            backLeft.setDirection(DcMotor.Direction.REVERSE);

            //Other Motor stuff
            angler.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            angler.setDirection(DcMotorSimple.Direction.FORWARD);

<<<<<<< HEAD:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/util/centerStageMachine.java
=======
            planeServo.setDirection(Servo.Direction.REVERSE);

>>>>>>> 2b08aeb0ca0ccf9a553a27c64e2acb6963fb8fd8:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Util/centerStageMachine.java

            // Drivetrain motors
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        }
}
