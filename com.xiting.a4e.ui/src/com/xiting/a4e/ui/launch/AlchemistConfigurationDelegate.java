package com.xiting.a4e.ui.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;

import com.xiting.a4e.backend.AlchemistRunnerFactory;
import com.xiting.a4e.backend.IAlchemistRunner;

public class AlchemistConfigurationDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("Running Xiting Alchemist", 0);

		IAlchemistRunner runner = AlchemistRunnerFactory.get();
		runner.run(configuration, launch);
		monitor.done();
	}



}
