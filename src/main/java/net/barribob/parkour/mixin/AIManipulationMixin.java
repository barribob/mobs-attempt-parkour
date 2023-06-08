package net.barribob.parkour.mixin;

import kotlin.Pair;
import net.barribob.maelstrom.MaelstromMod;
import net.barribob.parkour.Parkour;
import net.barribob.parkour.ai.AIInjector;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class AIManipulationMixin {
    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Inject(at = @At(value = "RETURN"), method = "<init>")
    private void goal(CallbackInfo info) {
        MobEntity entity = (MobEntity) (Object) this;
        if (entity.getEntityWorld() != null && !entity.getEntityWorld().isClient) {
            String mobId = EntityType.getId(entity.getType()).toString();
            AIInjector AIInjector = Parkour.INSTANCE.getAiInjector();

            if (AIInjector.getInjections().containsKey(mobId)) {
                AIInjector.getInjections().get(mobId).forEach((generator) -> {
                    Pair<Integer, Goal> pair = generator.invoke(entity);
                    if (pair.component2() != null) {
                        this.goalSelector.add(pair.component1(), pair.component2());
                    } else {
                        MaelstromMod.INSTANCE.getLOGGER().error("AI to be injected for " + mobId + " was null");
                    }
                });
            }
        }
    }
}
