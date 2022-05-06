package com.ixiting.a4e.backend;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.part.MultiPageEditorPart;

public interface IAlchemistRunner {
	public void run(MultiPageEditorPart editor);
	public void run(ISelection selection);
	public void run(ILaunchConfiguration configuration, ILaunch launch);
}
