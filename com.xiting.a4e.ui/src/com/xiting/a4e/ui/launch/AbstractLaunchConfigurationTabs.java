package com.xiting.a4e.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class AbstractLaunchConfigurationTabs extends AbstractLaunchConfigurationTabGroup {

	public AbstractLaunchConfigurationTabs() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {

		setTabs(new ILaunchConfigurationTab[] { new AbstractLaunchConfigurationScopeTab(),
				new AbstractLaunchConfigurationPatternsTab(), new AbstractLaunchConfigurationColumnsTab() });

	}

}
