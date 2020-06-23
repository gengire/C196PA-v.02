//*********************************************************************************
//  File:             SampleData.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

/**
 * Sample Data
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class SampleData {

    public static List<TermEntity> getSampleTerms() {
        List<TermEntity> list = new ArrayList<>();
        list.add(new TermEntity(1, "Fall 2018",
                getDate("October 1, 2018"), getDate("March 31, 2019")));
        list.add(new TermEntity(2, "Spring 2019",
                getDate("April 1, 2019"), getDate("September 30, 2019")));
        list.add(new TermEntity(3, "Term 3",
                getDate("October 1, 2019"), getDate("March 31, 2020")));
        list.add(new TermEntity(4, "Spring Term 2020",
                getDate("April 1, 2020"), getDate("September 30, 2020")));
        list.add(new TermEntity("Swipe right or left to delete me!",
                getDate("October 1, 2020"), getDate("March 31, 2021")));
        list.add(new TermEntity("Term w/ 0 Courses",
                getDate("April 1, 2021"), getDate("September 30, 2021")));
        list.add(new TermEntity("Sort of long term name",
                getDate("October 1, 2021"), getDate("March 31, 2022")));
        list.add(new TermEntity("A much longer term name just to see how the editor handles it",
                getDate("April 1, 2022"), getDate("September 30, 2022")));
        return list;
    }

    public static List<CourseEntity> getSampleCourses() {
        List<CourseEntity> list = new ArrayList<>();
        list.add(new CourseEntity(1, 1, 4, "C182",
                "Introduction to IT",
                getDate("October 10, 2018"), null,
                getDate("October 8, 2018"), null,"Complete"));
        list.add(new CourseEntity(2, 1, 3, "C173",
                "Scripting and Programming - Foundations",
                getDate("October 8, 2018"), null,
                getDate("October 10, 2018"), null, "Complete"));
        list.add(new CourseEntity(3, 1, 0, "ORA1",
                "Orientation",
                getDate("March 30, 2019"), null,
                getDate("April 1, 2019"), null,"Complete"));
        list.add(new CourseEntity(4, 1, 3, "C779",
                "Web Development Foundations",
                getDate("November 11, 2018"), null,
                getDate("November 13, 2018"), null,"Complete"));
        list.add(new CourseEntity(5, 1, 3, "C100",
                "Introduction to Humanities",
                getDate("October 30, 2018"), null,
                getDate("December 30, 2018"), null,"Complete"));
        list.add(new CourseEntity(6, 1, 4, "C993",
                "Structured Query Language",
                getDate("January 14, 2019"),null,
                getDate("January 30, 2019"), null,"Complete"));
        list.add(new CourseEntity(7, 1, 6, "C482",
                "Software I",
                getDate("February 11, 2019"), null,
                getDate("February 13, 2019"), null,"Complete"));
        list.add(new CourseEntity(8, 1, 6, "C195",
                "Software II - Advanced Java Concepts",
                getDate("February 25, 2019"), null,
                getDate("February 27, 2019"), null,"Complete"));
        list.add(new CourseEntity(9, 1, 3, "C175",
                "Data Management - Foundations",
                getDate("March 11, 2019"), null,
                getDate("March 12, 2019"), null,"Complete"));
        list.add(new CourseEntity(10, 1, 4, "C170",
                "Data Management - Applications",
                getDate("March 16, 2019"), null,
                getDate("March 18, 2019"), null,"Complete"));
        list.add(new CourseEntity(11, 2, 6, "C777",
                "Web Development Applications",
                getDate("April 1, 2019"), null,
                getDate("April 4, 2019"), null,"Complete"));
        list.add(new CourseEntity(12, 2, 3, "C172",
                "Network and Security - Foundations",
                getDate("May 20, 2019"), null,
                getDate("May 28, 2019"), null,"Complete"));
        list.add(new CourseEntity(13, 2, 4, "C176",
                "Business of IT - Project Management",
                getDate("June 19, 2019"), null,
                getDate("June 25, 2019"), null,"Complete"));
        list.add(new CourseEntity(14, 2, 4, "C393",
                "IT Foundations",
                getDate("July 7, 2019"), null,
                getDate("July 14, 2019"), null,"Complete"));
        list.add(new CourseEntity(15, 2, 3, "C857",
                "Software Quality Assurance",
                getDate("August 20, 2019"), null,
                getDate("August 27, 2019"), null,"Complete"));
        list.add(new CourseEntity(16, 2, 4, "C188",
                "Software Engineering",
                getDate("September 19, 2019"), null,
                getDate("September 26, 2019"), null,"Complete"));
        list.add(new CourseEntity(17, 3, 4, "C867",
                "Scripting and Programming - Applications",
                getDate("October 1, 2019"), null,
                getDate("October 8, 2019"), null,"Complete"));
        list.add(new CourseEntity(18, 3, 4, "C394",
                "IT Applications",
                getDate("October 15, 2019"), null,
                getDate("October 22, 2019"), null,"Complete"));
        list.add(new CourseEntity(19, 3, 4, "C846",
                "Business of IT - Applications",
                getDate("December 20, 2019"), null,
                getDate("January 5, 2020"), null,"Complete"));
        list.add(new CourseEntity(20, 3, 4, "C773",
                "User Interface Design",
                getDate("January 7, 2020"), null,
                getDate("January 12, 2020"), null,"Complete"));
        list.add(new CourseEntity(21, 3, 3, "C484",
                "Organizational Behavior and Leadership",
                getDate("January 26, 2020"), null,
                getDate("February 4, 2020"), null,"Complete"));
        list.add(new CourseEntity(22, 3, 3, "C856",
                "User Experience Design",
                getDate("February 1, 2020"), null,
                getDate("February 6, 2020"), null,"Complete"));
        list.add(new CourseEntity(23, 4, 3, "C191",
                "Operating Systems for Programmers",
                getDate("February 7, 2020"), null,
                getDate("May 18, 2020"), null,"Complete"));
        list.add(new CourseEntity(24, 4, 3, "C196",
                "Mobile Application Development",
                getDate("May 19, 2020"), null,
                getDate("June 22, 2020"), getDate("Jun 22, 2020"),"In Progress"));
        list.add(new CourseEntity(25, 4, 4, "C868",
                "Software Development Capstone",
                null, null,
                null, null,"Not Started"));
        return list;
    }

    public static List<NoteEntity> getSampleCourseNotes() {
        List<NoteEntity> list = new ArrayList<>();
        list.add(new NoteEntity(1, 1, "This is my first course note title",
                "This is my first course note description"));
        list.add(new NoteEntity(2, 1, "This is my second course note title",
                "This is my second course note description"));
        list.add(new NoteEntity(3, 1, "This is my third course note title",
                "This is my third course note description"));
        list.add(new NoteEntity(5, 24, "Mobile apps is pretty awesome",
                "I mean, I'm learning a ton and it's quite fun."));
        return list;
    }

    public static List<AssessmentEntity> getSampleAssessments() {
        List<AssessmentEntity> list = new ArrayList<>();
        list.add(new AssessmentEntity(1, 1,
                "Objective", "Introduction to IT",
                "Pass", getDate("October 8, 2018"), null));
        list.add(new AssessmentEntity(2, 2,
                "Objective", "Scripting and Programming - Foundations",
                "Pass", getDate("October 10, 2018"), null));
        list.add(new AssessmentEntity(3, 3,
                "Performance", "Education Without Boundaries Orientation",
                "Pass", getDate("September 23, 2018"), null));
        list.add(new AssessmentEntity(4, 4,
                "Objective", "CIW - Site Development Associate",
                "Pass", getDate("November 13, 2018"), null));
        list.add(new AssessmentEntity(5, 5,
                "Objective", "Introduction to Humanities",
                "Pass", getDate("December 30, 2018"), null));
        list.add(new AssessmentEntity(6, 5,
                "Performance", "Exploration of Humanities",
                "Pass", getDate("December 18, 2018"), null));
        list.add(new AssessmentEntity(7, 6,
                "Objective", "Oracle – Database SQL (1Z0-071)",
                "Pass", getDate("January 30, 2019"), null));
        list.add(new AssessmentEntity(8, 7,
                "Performance", "Software I - GYP1",
                "Pass", getDate("February 13, 2019"), null));
        list.add(new AssessmentEntity(9, 8,
                "Performance", "Software II - Advanced Java Concepts - GZP1",
                "Pass", getDate("February 28, 2019"), null));
        list.add(new AssessmentEntity(10, 9,
                "Objective", "Data Management - Foundations",
                "Pass", getDate("March 12, 2019"), null));
        list.add(new AssessmentEntity(11, 10,
                "Objective", "Data Management - Applications GSA1",
                "Pass", getDate("March 13, 2019"), null));
        list.add(new AssessmentEntity(12, 10,
                "Objective", "Data Management - Applications FJ01",
                "Pass", getDate("March 18, 2019"), null));
        list.add(new AssessmentEntity(13, 11,
                "Objective", "CIW - Advanced HTML5 and CSS3",
                "Pass", getDate("April 26, 2019"), null));
        list.add(new AssessmentEntity(14, 12,
                "Objective", "Network and Security Foundations",
                "Pass", getDate("May 28, 2019"), null));
        list.add(new AssessmentEntity(15, 13,
                "Objective", "CompTIA - Project+",
                "Pass", getDate("June 25, 2019"), null));
        list.add(new AssessmentEntity(16, 14,
                "Objective", "Third Party Assessment - CompTIA A+ Part 1/2",
                "Pass", getDate("August 14, 2019"), null));
        list.add(new AssessmentEntity(17, 15,
                "Objective", "Software Quality Assurance",
                "Pass", getDate("August 27, 2019"), null));
        list.add(new AssessmentEntity(18, 16,
                "Performance", "Software Engineering - NUP1",
                "Pass", getDate("September 26, 2019"), null));
        list.add(new AssessmentEntity(19, 17,
                "Performance", "Scripting and Programming - Applications - FPP1",
                "Pass", getDate("October 9, 2019"), null));
        list.add(new AssessmentEntity(20, 18,
                "Objective", "CompTIA - A+",
                "Pass", getDate("October 22, 2019"), null));
        list.add(new AssessmentEntity(21, 19,
                "Objective", "Axelos - ITIL Foundation Certification",
                "Pass", getDate("January 5, 2020"), null));
        list.add(new AssessmentEntity(22, 20,
                "Objective", "CIW - User Interface Designer",
                "Pass", getDate("January 12, 2020"), null));
        list.add(new AssessmentEntity(23, 21,
                "Objective", "Organizational Behavior and Leadership",
                "Pass", getDate("February 4, 2020"), null));
        list.add(new AssessmentEntity(24, 22,
                "Performance", "User Experience Design - HJP1",
                "Pass", getDate("February 6, 2019"), null));
        list.add(new AssessmentEntity(25, 23,
                "Objective", "Operating Systems for Programmers",
                "Pass", getDate("May 18, 2020"), null));
        list.add(new AssessmentEntity(26, 24,
                "Performance", "Mobile Application Development - ABM1",
                "Pending", getDate("June 22, 2020"), getDate("June 22, 2020")));
        list.add(new AssessmentEntity(27, 25,
                "Performance", "Software Development Capstone - EZP1",
                "Pending", getDate("September 30, 2020"), getDate("September 30, 2020")));
        return list;
    }

    public static List<MentorEntity> getSampleMentors() {
        List<MentorEntity> list = new ArrayList<>();
        list.add(new MentorEntity(1, "Course Instructor Group", "",
                "", "cmitfund1@wgu.edu"));
        list.add(new MentorEntity(2, "Course Instructor Group", "",
                "", "cmweb@wgu.edu"));
        list.add(new MentorEntity(3, "Course Instructor Group", "",
                "", "orientation@wgu.edu"));
        list.add(new MentorEntity(4, "Course Instructor Group", "",
                "", "cmweb@wgu.edu"));
        list.add(new MentorEntity(5, "Course Instructor Group", "",
                "", "humanities@wgu.egu"));
        list.add(new MentorEntity(6, "Student Services", "",
                "(877) 435-7948", "studentservices@wgu.edu"));
        list.add(new MentorEntity(7, "Course Instructor Group", "",
                "", "cmsoftware@wgu.edu"));
        list.add(new MentorEntity(8, "Course Instructor Group", "",
                "", "cmsoftware@wgu.edu"));
        list.add(new MentorEntity(9, "Course Instructor Group", "",
                "", "cmdatabase@wgu.edu"));
        list.add(new MentorEntity(10, "Course Instructor Group", "",
                "", "cmdatabase@wgu.edu"));
        list.add(new MentorEntity(11, "Course Instructor Group", "",
                "", "cmweb@wgu.edu"));
        list.add(new MentorEntity(12, "Course Instructor Group", "",
                "", "ciitnetworking@wgu.edu"));
        list.add(new MentorEntity(13, "Course Instructor Group", "",
                "", "cmitbusiness@wgu.edu"));
        list.add(new MentorEntity(14, "Course Instructor Group", "",
                "", "cmitfund1@wgu.edu"));
        list.add(new MentorEntity(15, "Course Instructor Group", "",
                "", "cmcomputerscience@wgu.edu"));
        list.add(new MentorEntity(16, "Course Instructor Group", "",
                "", "cmsoftware@wgu.edu"));
        list.add(new MentorEntity(17, "Course Instructor Group", "",
                "", "cmprogramming@wgu.edu"));
        list.add(new MentorEntity(18, "Course Instructor Group", "",
                "", "cmitfund1@wgu.edu"));
        list.add(new MentorEntity(19, "Course Instructor Group", "",
                "", "cmitbusiness@wgu.edu"));
        list.add(new MentorEntity(20, "Course Instructor Group", "",
                "", "cmweb@wgu.edu"));
        list.add(new MentorEntity(21, "Course Instructor Group", "",
                "", "orgleadership@wgu.edu"));
        list.add(new MentorEntity(22, "Course Instructor Group", "",
                "", "cmweb@wgu.edu"));
        list.add(new MentorEntity(23, "Course Instructor Group", "",
                "", "cmcomputerscience@wgu.edu"));
        list.add(new MentorEntity(24, "Alvaro", "Escobar",
                "(385) 428-8835", "alvaro.escobar@wgu.edu"));
        return list;
    }
}
