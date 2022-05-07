package com.xiting.a4e.model;

import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

public class Editor2AbapParser implements IAdt2AbapParser {
	private MultiPageEditorPart editor;

	Editor2AbapParser(MultiPageEditorPart editor) {
		this.editor = editor;
	}

	@Override
	public String getName() {
		if (isFunctionInclude()) {
			return removeProject(getGroupFromInclude());
		} else {
			return removeProject(editor.getTitle());
		}
	}

	private String removeProject(String name) {
		String[] parts = name.split(" ");
		return parts[parts.length - 1];
	}

	private String getGroupFromInclude() {
		IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
		String fileName = editorInput.getFile().getName();
		return Adt2AbapParserFactory.getGroupFromFunctionInclude(fileName);
	}

	private boolean isFunctionInclude() {
		IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
		return editorInput.getFile().getFileExtension() == "asfinc";
	}

	@Override
	public String getType() {
		IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
		String type = "";
		switch (editorInput.getFile().getFileExtension()) {
		case "aclass":
			type = "CLAS";
			break;
		case "asfunc":
			type = "FUNC";
			break;
		case "asprog":
			type = "PROG";
			break;
		case "asfugr":
		case "asfinc":
			type = "FUGR";
			break;
		case "aint":
			type = "INTF";
			break;
		}
		return type;
	}

}
