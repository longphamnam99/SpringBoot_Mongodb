/*
 Navicat Premium Data Transfer

 Source Server         : MongoDB Local
 Source Server Type    : MongoDB
 Source Server Version : 60003
 Source Host           : localhost:27017
 Source Schema         : iuhdb

 Target Server Type    : MongoDB
 Target Server Version : 60003
 File Encoding         : 65001

 Date: 20/05/2023 20:42:37
*/


// ----------------------------
// Collection structure for class_rooms
// ----------------------------
db.getCollection("class_rooms").drop();
db.createCollection("class_rooms");

// ----------------------------
// Documents of class_rooms
// ----------------------------
session = db.getMongo().startSession();
session.startTransaction();
db = session.getDatabase("iuhdb");
db.getCollection("class_rooms").insert([ {
    _id: ObjectId("645b4ff87de1535df7240925"),
    major: ObjectId("645511d3d4103844bfcf7782"),
    name: "18BVL",
    "created_at": "10-05-2023 15:04:08"
} ]);
db.getCollection("class_rooms").insert([ {
    _id: ObjectId("645b54cfcc2277697de78c09"),
    major: ObjectId("645511f7ff0246036e6ffdf9"),
    name: "17BVL",
    "created_at": "10-05-2023 15:24:47"
} ]);
db.getCollection("class_rooms").insert([ {
    _id: ObjectId("645e21fd1cb5e83cecc32838"),
    major: ObjectId("645e21e81cb5e83cecc32831"),
    name: "17BVL",
    "created_at": "12-05-2023 18:24:45"
} ]);
session.commitTransaction(); session.endSession();

// ----------------------------
// Collection structure for majors
// ----------------------------
db.getCollection("majors").drop();
db.createCollection("majors");

// ----------------------------
// Documents of majors
// ----------------------------
session = db.getMongo().startSession();
session.startTransaction();
db = session.getDatabase("iuhdb");
db.getCollection("majors").insert([ {
    _id: ObjectId("645e21e81cb5e83cecc32831"),
    code: "DHCNT",
    name: "Công nghệ thông tin",
    "created_at": "12-05-2023 18:24:24"
} ]);
session.commitTransaction(); session.endSession();

// ----------------------------
// Collection structure for students
// ----------------------------
db.getCollection("students").drop();
db.createCollection("students");

// ----------------------------
// Documents of students
// ----------------------------
session = db.getMongo().startSession();
session.startTransaction();
db = session.getDatabase("iuhdb");
db.getCollection("students").insert([ {
    _id: ObjectId("645b630e99e09d6c5d32499c"),
    name: "Phạm Hồng Long",
    "class_room_id": ObjectId("645b4ff87de1535df7240925"),
    gender: "1",
    "date_of_birth": "1999-07-18",
    "created_at": "10-05-2023 16:25:34"
} ]);
db.getCollection("students").insert([ {
    _id: ObjectId("645bce2cd509463226dc3ec6"),
    name: "Phạm Nữ Long",
    "class_room_id": ObjectId("645b54cfcc2277697de78c09"),
    gender: "0",
    "date_of_birth": "1998-07-18",
    "created_at": "11-05-2023 00:02:36"
} ]);
db.getCollection("students").insert([ {
    _id: ObjectId("645e22171cb5e83cecc3283e"),
    name: "Phạm Nam Long",
    "class_room_id": ObjectId("645e21fd1cb5e83cecc32838"),
    gender: "0",
    "date_of_birth": "1999-07-18",
    "created_at": "12-05-2023 18:25:11"
} ]);
session.commitTransaction(); session.endSession();

// ----------------------------
// Collection structure for users
// ----------------------------
db.getCollection("users").drop();
db.createCollection("users");

// ----------------------------
// Documents of users
// ----------------------------
session = db.getMongo().startSession();
session.startTransaction();
db = session.getDatabase("iuhdb");
db.getCollection("users").insert([ {
    _id: ObjectId("645d0d85aa07d733d5d4351e"),
    email: "long@iuh.vn",
    password: "123456",
    "created_at": "11-05-2023 22:45:09"
} ]);
session.commitTransaction(); session.endSession();
