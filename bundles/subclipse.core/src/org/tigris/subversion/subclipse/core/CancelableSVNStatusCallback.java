/**
 * ***************************************************************************** Copyright (c) 2003,
 * 2019 Subclipse project and others. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Subclipse project committers - initial API and implementation
 * ****************************************************************************
 */
package org.tigris.subversion.subclipse.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.tigris.subversion.svnclientadapter.ISVNClientAdapter;
import org.tigris.subversion.svnclientadapter.ISVNStatus;
import org.tigris.subversion.svnclientadapter.SVNClientException;
import org.tigris.subversion.svnclientadapter.SVNStatusCallback;

public class CancelableSVNStatusCallback extends SVNStatusCallback {
  private IProgressMonitor monitor;
  private ISVNClientAdapter svnClient;
  private boolean canceled;

  public CancelableSVNStatusCallback(IProgressMonitor monitor) {
    super();
    this.monitor = monitor;
  }

  public void setSvnClient(ISVNClientAdapter svnClient) {
    this.svnClient = svnClient;
  }

  @Override
  public void doStatus(String path, ISVNStatus status) {
    super.doStatus(path, status);
    if (svnClient != null && monitor != null && monitor.isCanceled() && !canceled) {
      try {
        svnClient.cancelOperation();
        canceled = true;
      } catch (SVNClientException e) {
      }
    }
  }
}
