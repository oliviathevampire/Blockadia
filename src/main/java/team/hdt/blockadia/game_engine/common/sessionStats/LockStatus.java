package team.hdt.blockadia.game_engine.common.sessionStats;

import team.hdt.blockadia.game_engine.util.BinaryReader;
import team.hdt.blockadia.game_engine.util.BinaryWriter;

import java.util.HashSet;
import java.util.Set;

public class LockStatus {

    private Set<Integer> unlockedBlueprints = new HashSet<Integer>();

    private LockStatus() {
    }

    public static LockStatus loadLockStatus(BinaryReader reader) throws Exception {
        LockStatus lockStatus = new LockStatus();
        int count = reader.readInt();
        for (int i = 0; i < count; i++) {
            int blueprintID = reader.readInt();
            lockStatus.unlockedBlueprints.add(blueprintID);
        }
        return lockStatus;
    }

    public static LockStatus newLockStatus() {
        LockStatus lock = new LockStatus();
        return lock;
    }

    public int getUnlockedCount() {
        return unlockedBlueprints.size();
    }

    public Set<Integer> getUnlockedSpecies() {
        return unlockedBlueprints;
    }

    public void export(BinaryWriter writer) {
        writer.writeInt(unlockedBlueprints.size());
        for (Integer id : unlockedBlueprints) {
            writer.writeInt(id);
        }
    }

}
