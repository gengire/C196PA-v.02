//*********************************************************************************
//  File:             ValidationCallback.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.utilities.validation;

/**
 * Callback to pass in validation success and failure strategies
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public interface ValidationCallback {

    /**
     * Success or Failure strategy hook
     */
    void callback();

}
