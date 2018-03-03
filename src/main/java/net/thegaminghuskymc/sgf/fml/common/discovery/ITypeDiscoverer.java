package net.thegaminghuskymc.sgf.fml.common.discovery;

import net.thegaminghuskymc.sgf.fml.common.ModContainer;

import java.util.List;
import java.util.regex.Pattern;

public interface ITypeDiscoverer
{
    // main class part, followed by an optional $ and an "inner class" part. $ cannot be last, otherwise scala breaks
    public static Pattern classFile = Pattern.compile("[^\\s\\$]+(\\$[^\\s]+)?\\.class$");

    List<ModContainer> discover(ModCandidate candidate, ASMDataTable table);
}