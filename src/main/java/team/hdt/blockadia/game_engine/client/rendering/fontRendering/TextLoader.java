package team.hdt.blockadia.game_engine.client.rendering.fontRendering;

import team.hdt.blockadia.game_engine.client.rendering.textures.Texture;
import team.hdt.blockadia.game_engine.common.Loader;
import team.hdt.blockadia.game_engine.util.MyFile;

import java.util.ArrayList;
import java.util.List;

public class TextLoader {

	protected static final double LINE_HEIGHT = 0.04f;
	protected static final int SPACE_ASCII = 32;

	private Texture fontTexture;
	private MetaFile metaData;

	public TextLoader(MyFile fontSheet, MyFile metaFile) {
		fontTexture = Texture.newTexture(fontSheet).noFiltering().create();
		metaData = new MetaFile(metaFile);
	}

	public int getFontTextureAtlas() {
		return fontTexture.getID();
	}

	public void loadTextIntoMemory(Text text) {
		List<Line> lines = createStructure(text);
		loadStructureToOpenGL(text, lines);
		// GUIManager.addNewText(text);
	}

	private List<Line> createStructure(Text text) {
		char[] chars = text.getTextString().toCharArray();
		List<Line> lines = new ArrayList<Line>();
		Line currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
		Word currentWord = new Word(text.getFontSize());
		for (char c : chars) {
			int ascii = (int) c;
			if (ascii == SPACE_ASCII) {
				boolean added = currentLine.attemptToAddWord(currentWord);
				if (!added) {
					lines.add(currentLine);
					currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
					currentLine.attemptToAddWord(currentWord);
				}
				currentWord = new Word(text.getFontSize());
				continue;
			}
			Character character = metaData.getCharacter(ascii);
			if (character != null) {
				currentWord.addCharacter(character);
			}else{
				System.err.println("ERROR CHAR " + ascii);
			}
		}
		completeStructure(lines, currentLine, currentWord, text);
		return lines;
	}

	private void completeStructure(List<Line> lines, Line currentLine, Word currentWord, Text text) {
		boolean added = currentLine.attemptToAddWord(currentWord);
		if (!added) {
			lines.add(currentLine);
			currentLine = new Line(metaData.getSpaceWidth(), text.getFontSize(), text.getMaxLineSize());
			currentLine.attemptToAddWord(currentWord);
		}
		lines.add(currentLine);
	}

	private void loadStructureToOpenGL(Text text, List<Line> lines) {
		setTextSettings(text, lines);
		boolean first = true;
		double indent = 0;
		double curserX = 0f;
		double curserY = 0f;
		List<Float> vertices = new ArrayList<Float>();
		List<Float> textureCoords = new ArrayList<Float>();
		for (int i = 0; i < lines.size(); i++) {
			Line line = lines.get(i);
			curserX = indent;
			if (text.isCentered()) {
				curserX = (line.getMaxLength() - line.getLineLength()) / 2;
			} else if (text.isRightAligned()) {
				curserX = line.getMaxLength() - line.getLineLength();
			}
			double extraSpace = (!text.isJustified() || i == lines.size() - 1) ? 0
					: (line.getMaxLength() - line.getLineLength()) / (line.getWords().size() - 1);
			for (Word word : line.getWords()) {
				for (Character letter : word.getCharacters()) {
					addVerticesForCharacter(curserX, curserY, letter, text.getFontSize(), vertices);
					addTextCoords(textureCoords, letter.getxTextureCoord(), letter.getyTextureCoord(),
							letter.getXMaxTextureCoord(), letter.getYMaxTextureCoord());
					curserX += letter.getxAdvance() * text.getFontSize();
				}
				curserX += (metaData.getSpaceWidth()) * text.getFontSize() + extraSpace;
				if (first && text.isIndented()) {
					indent = curserX;
					first = false;
				}
			}
			curserY += LINE_HEIGHT * text.getFontSize();
		}
		float[] verticesArray = listToArray(vertices);
		float[] textureArray = listToArray(textureCoords);
		int vao = Loader.createInterleavedVAO(vertices.size() / 2, verticesArray, textureArray);
		text.setMeshInfo(vao, vertices.size() / 2, (float) lines.get(0).getLineLength(), (float) curserY);
	}

	private void addVerticesForCharacter(double curserX, double curserY, Character character, double fontSize,
                                         List<Float> vertices) {
		double x = curserX + (character.getxOffset() * fontSize);
		double y = curserY + (character.getyOffset() * fontSize);
		double maxX = x + (character.getSizeX() * fontSize);
		double maxY = y + (character.getSizeY() * fontSize);
		addVertices(vertices, x, y, maxX, maxY);
	}

	private static void addVertices(List<Float> vertices, double x, double y, double maxX, double maxY) {
		vertices.add((float) x);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) maxY);
		vertices.add((float) maxX);
		vertices.add((float) y);
		vertices.add((float) x);
		vertices.add((float) y);
	}

	private static void addTextCoords(List<Float> texCoords, double x, double y, double maxX, double maxY) {

		texCoords.add((float) x);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) maxY);
		texCoords.add((float) maxX);
		texCoords.add((float) y);
		texCoords.add((float) x);
		texCoords.add((float) y);
	}

	private void setTextSettings(Text text, List<Line> lines) {
		text.setNumberOfLines(lines.size());
		if (text.isCentered() || lines.size() > 1) {
			text.setOriginalWidth((float) lines.get(0).getMaxLength());
		} else {
			text.setOriginalWidth((float) lines.get(0).getLineLength());
		}
	}

	private float[] listToArray(List<Float> list) {
		float[] array = new float[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

}
