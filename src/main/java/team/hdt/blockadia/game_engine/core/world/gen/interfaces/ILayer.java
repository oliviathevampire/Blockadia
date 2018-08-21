package team.hdt.blockadia.game_engine.core.world.gen.interfaces;


import team.hdt.blockadia.game_engine.core.block.BlockType;
import team.hdt.blockadia.game_engine.core.init.Blocks;

public interface ILayer {

    public int getWidth();

    public int getHeight();

    public BlockType getBlockAtPos(int x, int z);

    public class LayerAir implements ILayer {

        private final int WIDTH, HEIGHT;

        public LayerAir(int width, int height) {
            this.HEIGHT = height;
            this.WIDTH = width;
        }

        @Override
        public int getWidth() {
            return this.WIDTH;
        }

        @Override
        public int getHeight() {
            return this.HEIGHT;
        }

        @Override
        public BlockType getBlockAtPos(int x, int z) {
            return Blocks.AIR;
        }
    }

}