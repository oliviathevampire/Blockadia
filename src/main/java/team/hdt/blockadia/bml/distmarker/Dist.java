package team.hdt.blockadia.bml.distmarker;

public enum Dist {
   CLIENT,
   DEDICATED_SERVER;

   public boolean isDedicatedServer() {
      return !this.isClient();
   }

   public boolean isClient() {
      return this == CLIENT;
   }
}
