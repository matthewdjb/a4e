package com.xiting.a4e.backend;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.part.MultiPageEditorPart;

public interface IAlchemistRunner {
	public void run(MultiPageEditorPart editor);
	public void run(ISelection selection);
}