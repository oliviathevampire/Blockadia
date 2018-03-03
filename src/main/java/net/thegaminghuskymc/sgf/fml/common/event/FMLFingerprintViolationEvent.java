package net.thegaminghuskymc.sgf.fml.common.event;

import com.google.common.collect.ImmutableSet;

import java.io.File;
import java.util.Set;

public class FMLFingerprintViolationEvent extends FMLEvent {

    private final boolean isDirectory;
    private final Set<String> fingerprints;
    private final File source;
    private final String expectedFingerprint;

    public FMLFingerprintViolationEvent(boolean isDirectory, File source, ImmutableSet<String> fingerprints, String expectedFingerprint)
    {
        super();
        this.isDirectory = isDirectory;
        this.source = source;
        this.fingerprints = fingerprints;
        this.expectedFingerprint = expectedFingerprint;
    }

    public boolean isDirectory() { return isDirectory; }
    public Set<String> getFingerprints() { return fingerprints; }
    public File getSource() { return source; }
    public String getExpectedFingerprint() { return expectedFingerprint; }
}