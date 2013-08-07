/*******************************************************************************
 * Copyright (C) 2010 - 2013 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 * 
 * Unless you have purchased a commercial license agreement from Jaspersoft, 
 * the following license terms apply:
 * 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Jaspersoft Studio Team - initial API and implementation
 ******************************************************************************/
package com.jaspersoft.studio.server.dnd;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.TreeItem;

import com.jaspersoft.jasperserver.api.metadata.xml.domain.impl.ResourceDescriptor;
import com.jaspersoft.studio.dnd.NodeTransfer;
import com.jaspersoft.studio.dnd.NodeTreeDropAdapter;
import com.jaspersoft.studio.model.ANode;
import com.jaspersoft.studio.model.INode;
import com.jaspersoft.studio.server.ServerManager;
import com.jaspersoft.studio.server.WSClientHelper;
import com.jaspersoft.studio.server.model.MInputControl;
import com.jaspersoft.studio.server.model.MReportUnit;
import com.jaspersoft.studio.server.model.server.MServerProfile;
import com.jaspersoft.studio.server.protocol.IConnection;

/**
 * A target drop listener that creates a generic file resource element when
 * something is dropped on the JRS repository tree.
 * 
 * @author Massimo Rabbi (mrabbi@users.sourceforge.net)
 * 
 */
public class InputControlDropTargetListener extends NodeTreeDropAdapter implements TransferDropTargetListener {

	public InputControlDropTargetListener(TreeViewer treeViewer) {
		super(treeViewer);
	}

	@Override
	public boolean performDrop(Object data) {
		if (data == null)
			return false;
		final List<MInputControl> mc = new ArrayList<MInputControl>();
		if (data.getClass().isArray()) {
			Object[] ar = (Object[]) data;
			for (Object obj : ar)
				if (obj instanceof MInputControl)
					mc.add((MInputControl) obj);
		} else if (data instanceof MInputControl)
			mc.add((MInputControl) data);

		Job job = new Job(com.jaspersoft.studio.messages.Messages.common_reorder_elements) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IStatus status = Status.OK_STATUS;
				monitor.beginTask(com.jaspersoft.studio.messages.Messages.common_reorder_elements, IProgressMonitor.UNKNOWN);
				try {
					Object target = getCurrentTarget();
					if (target instanceof ANode && ((ANode) target).getParent() instanceof MReportUnit)
						status = doRun((ANode) target, mc, monitor);
				} finally {
					monitor.done();
				}
				return status;
			}
		};
		job.setPriority(Job.LONG);
		job.setUser(true);
		job.schedule();
		return true;
	}

	protected IStatus doRun(ANode target, List<MInputControl> toMove, IProgressMonitor monitor) {
		MReportUnit mrunit = null;
		if (target instanceof MReportUnit)
			mrunit = (MReportUnit) target;
		else if (target.getParent() instanceof MReportUnit)
			mrunit = (MReportUnit) ((ANode) target).getParent();

		int indx = mrunit.getChildren().indexOf(target);

		List<MInputControl> tm = new ArrayList<MInputControl>();
		for (INode n : mrunit.getChildren()) {
			if (n instanceof MInputControl) {
				String uri = ((MInputControl) n).getValue().getUriString();
				for (MInputControl mc : toMove) {
					if (mc.getValue().getUriString().equals(uri)) {
						tm.add((MInputControl) n);
						break;
					}
				}
			}
		}
		// move elements here
		mrunit.removeChildren(tm);

		for (int i = 0; i < tm.size(); i++)
			mrunit.addChild(tm.get(i), i + indx);

		List<MInputControl> ics = doBuildICList(mrunit);
		IConnection c = doGetFullResources(mrunit, ics);
		doSaveBack(c, mrunit.getValue().getUriString(), ics);
		ServerManager.selectIfExists(monitor, mrunit);
		return Status.OK_STATUS;
	}

	protected List<MInputControl> doBuildICList(MReportUnit mrunit) {
		List<MInputControl> ics = new ArrayList<MInputControl>();
		for (INode n : mrunit.getChildren())
			if (n instanceof MInputControl)
				ics.add((MInputControl) n);
		return ics;
	}

	protected IConnection doGetFullResources(MReportUnit mrunit, List<MInputControl> ics) {
		IConnection c = ((MServerProfile) mrunit.getRoot()).getWsClient();
		for (MInputControl n : ics) {
			try {
				ResourceDescriptor rd = n.getValue();
				n.setValue(WSClientHelper.getResource(c, rd, null));
				c.delete(rd, mrunit.getValue().getUriString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}

	protected void doSaveBack(IConnection c, String ruuri, List<MInputControl> ics) {
		for (MInputControl n : ics) {
			try {
				n.getValue().setIsNew(true);
				c.modifyReportUnitResource(ruuri, n.getValue(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isEnabled(DropTargetEvent event) {
		if (event.item instanceof TreeItem) {
			TreeItem item = (TreeItem) event.item;
			Object d = item.getData();
			if (d instanceof MInputControl && ((MInputControl) d).getParent() instanceof MReportUnit)
				return true;
		}
		return false;
	}

	@Override
	public Transfer getTransfer() {
		return NodeTransfer.getInstance();
	}

}
