package edu.wgu.grimes.c196pa.utilities;

import java.util.ArrayList;
import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getDate;

public class SampleData {

    public static List<TermEntity> getSampleTerms() {
        List<TermEntity> list = new ArrayList<>();
        list.add(new TermEntity(1,"Term 1",
                getDate("October 1, 2018"), getDate("March 31, 2019")));
        list.add(new TermEntity(2,"Term 2",
                getDate("April 1, 2019"), getDate("September 30, 2019")));
        list.add(new TermEntity(3,"Term 3",
                getDate("October 1, 2019"), getDate("March 31, 2020")));
        list.add(new TermEntity(4,"Term 4",
                getDate("April 1, 2020"), getDate("September 30, 2020")));
        list.add(new TermEntity("Term 5",
                getDate("October 1, 2020"), getDate("March 31, 2021")));
        list.add(new TermEntity("Term 6",
                getDate("April 1, 2021"), getDate("September 30, 2021")));
        list.add(new TermEntity("Term 7",
                getDate("October 1, 2021"), getDate("March 31, 2022")));
        list.add(new TermEntity("Term 8",
                getDate("April 1, 2022"), getDate("September 30, 2022")));
        return list;
    }

    public static List<CourseEntity> getSampleCourses() {
        List<CourseEntity> list = new ArrayList<>();
        list.add(new CourseEntity(1, 1, 4, "C182",
                "Introduction to IT",
                getDate("October 10, 2018"),
                getDate("October 8, 2018"), "Complete"));
        list.add(new CourseEntity(2, 1, 3, "C173",
                        "Scripting and Programming - Foundations",
                        getDate("October 8, 2018"),
                        getDate("October 10, 2018"), "Complete"));
        list.add(new CourseEntity(3, 1, 0, "ORA1",
                        "Orientation",
                        getDate("March 30, 2019"),
                        getDate("April 1, 2019"), "Complete"));
        list.add(new CourseEntity(4, 1, 3, "C779",
                        "Web Development Foundations",
                        getDate("November 11, 2018"),
                        getDate("November 13, 2018"), "Complete"));
        list.add(new CourseEntity(5, 1, 3, "C100",
                        "Introduction to Humanities",
                        getDate("October 30, 2018"),
                        getDate("December 30, 2018"), "Complete"));
        list.add(new CourseEntity(6, 1, 4, "C993",
                        "Structured Query Language",
                        getDate("January 14, 2019"),
                        getDate("January 30, 2019"), "Complete"));
        list.add(new CourseEntity(7, 1, 6, "C482",
                        "Software I",
                        getDate("February 11, 2019"),
                        getDate("February 13, 2019"), "Complete"));
        list.add(new CourseEntity(8, 1, 6, "C195",
                        "Software II - Advanced Java Concepts",
                        getDate("February 25, 2019"),
                        getDate("February 27, 2019"), "Complete"));
        list.add(new CourseEntity(9, 1, 3, "C175",
                        "Data Management - Foundations",
                        getDate("March 11, 2019"),
                        getDate("March 12, 2019"), "Complete"));
        list.add(new CourseEntity(10, 1, 4, "C170",
                        "Data Management - Applications",
                        getDate("March 16, 2019"),
                        getDate("March 18, 2019"), "Complete"));
        list.add(new CourseEntity(11, 2, 6, "C777",
                        "Web Development Applications",
                        getDate("April 1, 2019"),
                        getDate("April 4, 2019"), "Complete"));
        list.add(new CourseEntity(12, 2, 3, "C172",
                        "Network and Security - Foundations",
                        getDate("May 20, 2019"),
                        getDate("May 28, 2019"), "Complete"));
        list.add(new CourseEntity(13, 2, 4, "C176",
                        "Business of IT - Project Management",
                        getDate("June 19, 2019"),
                        getDate("June 25, 2019"), "Complete"));
        list.add(new CourseEntity(14, 2, 4, "C393",
                        "IT Foundations",
                        getDate("July 7, 2019"),
                        getDate("July 14, 2019"), "Complete"));
        list.add(new CourseEntity(15, 2, 3, "C857",
                        "Software Quality Assurance",
                        getDate("August 20, 2019"),
                        getDate("August 27, 2019"), "Complete"));
        list.add(new CourseEntity(16, 2, 4, "C188",
                        "Software Engineering",
                        getDate("September 19, 2019"),
                        getDate("September 26, 2019"), "Complete"));
        list.add(new CourseEntity(17, 3, 4, "C867",
                        "Scripting and Programming - Applications",
                        getDate("October 1, 2019"),
                        getDate("October 8, 2019"), "Complete"));
        list.add(new CourseEntity(18, 3, 4, "C394",
                        "IT Applications",
                        getDate("October 15, 2019"),
                        getDate("October 22, 2019"), "Complete"));
        list.add(new CourseEntity(19, 3, 4, "C846",
                        "Business of IT - Applications",
                        getDate("December 20, 2019"),
                        getDate("January 5, 2020"), "Complete"));
        list.add(new CourseEntity(20, 3, 4, "C773",
                        "User Interface Design",
                        getDate("January 7, 2020"),
                        getDate("January 12, 2020"), "Complete"));
        list.add(new CourseEntity(21, 3, 3, "C484",
                        "Organizational Behavior and Leadership",
                        getDate("January 26, 2020"),
                        getDate("February 4, 2020"), "Complete"));
        list.add(new CourseEntity(22, 3, 3, "C856",
                        "User Experience Design",
                        getDate("February 1, 2020"),
                        getDate("February 6, 2020"), "Complete"));
        list.add(new CourseEntity(23, 4, 3, "C191",
                        "Operating Systems for Programmers",
                        getDate("February 7, 2020"),
                        getDate("May 18, 2020"), "Complete"));
        list.add(new CourseEntity(24, 4, 3, "C196",
                        "Mobile Application Development",
                        getDate("May 19, 2020"),
                        null, "In Progress"));
        list.add(new CourseEntity(25, 4, 4, "C868",
                        "Software Development Capstone",
                        null,
                        null, "In Progress"));
        return list;
    }

    public static List<AssessmentEntity> getSampleAssessments() {
        List<AssessmentEntity> list = new ArrayList<>();
        list.add(new AssessmentEntity(1, 1,
                "Objective", "Introduction to IT",
                "Pass", getDate("October 8, 2018")));
        list.add(new AssessmentEntity(2, 2,
                "Objective", "Scripting and Programming - Foundations",
                "Pass", getDate("October 10, 2018")));
        list.add(new AssessmentEntity(3, 3,
                "Performance", "Education Without Boundaries Orientation",
                "Pass", getDate("September 23, 2018")));
        list.add(new AssessmentEntity(4, 4,
                "Objective", "CIW - Site Development Associate",
                "Pass", getDate("November 13, 2018")));
        list.add(new AssessmentEntity(5, 5,
                "Objective", "Introduction to Humanities",
                "Pass", getDate("December 30, 2018")));
        list.add(new AssessmentEntity(6, 5,
                "Performance", "Exploration of Humanities",
                "Pass", getDate("December 18, 2018")));
        list.add(new AssessmentEntity(7, 6,
                "Objective", "Oracle – Database SQL (1Z0-071)",
                "Pass", getDate("January 30, 2019")));
        list.add(new AssessmentEntity(8, 7,
                "Performance", "Software I - GYP1",
                "Pass", getDate("February 13, 2019")));
        list.add(new AssessmentEntity(9, 8,
                "Performance", "Software II - Advanced Java Concepts - GZP1",
                "Pass", getDate("February 28, 2019")));
        list.add(new AssessmentEntity(10, 9,
                "Objective", "Data Management - Foundations",
                "Pass", getDate("March 12, 2019")));
        list.add(new AssessmentEntity(11, 10,
                "Objective", "Data Management - Applications GSA1",
                "Pass", getDate("March 13, 2019")));
        list.add(new AssessmentEntity(12, 10,
                "Objective", "Data Management - Applications FJ01",
                "Pass", getDate("March 18, 2019")));
        list.add(new AssessmentEntity(13, 11,
                "Objective", "CIW - Advanced HTML5 and CSS3",
                "Pass", getDate("April 26, 2019")));
        list.add(new AssessmentEntity(14, 12,
                "Objective", "Network and Security Foundations",
                "Pass", getDate("May 28, 2019")));
        list.add(new AssessmentEntity(15, 13,
                "Objective", "CompTIA - Project+",
                "Pass", getDate("June 25, 2019")));
        list.add(new AssessmentEntity(16, 14,
                "Objective", "Third Party Assessment - CompTIA A+ Part 1/2",
                "Pass", getDate("August 14, 2019")));
        list.add(new AssessmentEntity(17, 15,
                "Objective", "Software Quality Assurance",
                "Pass", getDate("August 27, 2019")));
        list.add(new AssessmentEntity(18, 16,
                "Performance", "Software Engineering - NUP1",
                "Pass", getDate("September 26, 2019")));
        list.add(new AssessmentEntity(19, 17,
                "Performance", "Scripting and Programming - Applications - FPP1",
                "Pass", getDate("October 9, 2019")));
        list.add(new AssessmentEntity(20, 18,
                "Objective", "CompTIA - A+",
                "Pass", getDate("October 22, 2019")));
        list.add(new AssessmentEntity(21, 19,
                "Objective", "Axelos - ITIL Foundation Certification",
                "Pass", getDate("January 5, 2020")));
        list.add(new AssessmentEntity(22, 20,
                "Objective", "CIW - User Interface Designer",
                "Pass", getDate("January 12, 2020")));
        list.add(new AssessmentEntity(23, 21,
                "Objective", "Organizational Behavior and Leadership",
                "Pass", getDate("February 4, 2020")));
        list.add(new AssessmentEntity(24, 22,
                "Performance", "User Experience Design - HJP1",
                "Pass", getDate("February 6, 2019")));
        list.add(new AssessmentEntity(25, 23,
                "Objective", "Operating Systems for Programmers",
                "Pass", getDate("May 18, 2020")));
        list.add(new AssessmentEntity(26, 24,
                "Performance", "Mobile Application Development - ABM1",
                "Pending", null));
        list.add(new AssessmentEntity(27, 25,
                "Performance", "Software Development Capstone - EZP1",
                "Pending", null));
        return list;
    }
}