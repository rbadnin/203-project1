/*
Action: ideally what our various entities might do in our virutal world
 */

final class Action
{
   public ActionKind kind;
   public Entity entity;
   public WorldModel world;
   public ImageStore imageStore;
   public int repeatCount;

   public Action(ActionKind kind, Entity entity, WorldModel world,
      ImageStore imageStore, int repeatCount)
   {
      this.kind = kind;
      this.entity = entity;
      this.world = world;
      this.imageStore = imageStore;
      this.repeatCount = repeatCount;
   }

   public void executeAction( EventScheduler scheduler)
   {
      switch (kind)
      {
         case ACTIVITY:
            executeActivityAction(scheduler);
            break;

         case ANIMATION:
            executeAnimationAction(scheduler);
            break;
      }
   }

   public void executeAnimationAction(EventScheduler scheduler)
   {
      entity.nextImage();

      if (repeatCount != 1)
      {
         scheduleEvent(scheduler, entity,
                 createAnimationAction(entity, Math.max(repeatCount - 1, 0)),
                 entity.getAnimationPeriod());
      }
   }

   public void executeActivityAction(EventScheduler scheduler)
   {
      switch (entity.kind)
      {
         case OCTO_FULL:
            executeOctoFullActivity(entity, world, imageStore, scheduler);
            break;

         case OCTO_NOT_FULL:
            executeOctoNotFullActivity(entity, world, imageStore, scheduler);
            break;

         case FISH:
            executeFishActivity(entity, world, imageStore,
                    scheduler);
            break;

         case CRAB:
            executeCrabActivity(entity, world, imageStore, scheduler);
            break;

         case QUAKE:
            executeQuakeActivity(entity, world, imageStore, scheduler);
            break;

         case SGRASS:
            executeSgrassActivity(entity, world, imageStore, scheduler);
            break;

         case ATLANTIS:
            executeAtlantisActivity(entity, world, imageStore, scheduler);
            break;

         default:
            throw new UnsupportedOperationException(
                    String.format("executeActivityAction not supported for %s",
                            entity.kind));
      }
   }
}
