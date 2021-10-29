// Copyright 2021 Red Date Technology Ltd.  Licensed under MPLv2
// (https://www.mozilla.org/en-US/MPL/2.0/)
package com.reddate.did.sdk.param.req;

/**
 * 
 * Can grant permission information data structure
 * 
 *
 */
public enum Operation {
  /**
   * Write permission
   */
  WRITE,
  /**
   * Update permission
   */
  UPDATE,
  /**
   * Read permission
   */
  READ,
  /**
   * Delete permission
   */
  DELETE
}
