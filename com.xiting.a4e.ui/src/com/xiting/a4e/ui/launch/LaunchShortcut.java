package com.xiting.a4e.ui.launch;

import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.ixiting.a4e.backend.AlchemistRunnerFactory;
import com.ixiting.a4e.backend.IAlchemistRunner;

public class LaunchShortcut implements ILaunchShortcut {
	@Override
	public void launch(ISelection selection, String mode) {
		IAlchemistRunner runner = AlchemistRunnerFactory.get();
		runner.run(selection);
	}

	@Override
	public void launch(IEditorPart editorPart, String mode) {
		MultiPageEditorPart editor = (MultiPageEditorPart) editorPart;
		IAlchemistRunner runner = AlchemistRunnerFactory.get();
		runner.run(editor);
	}

}
