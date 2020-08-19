package mod.azure.mtca.mixin;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.hyper_pigeon.moretotems.MoreTotemsMod;
import net.hyper_pigeon.moretotems.SummonedBeeEntity;
import net.hyper_pigeon.moretotems.SummonedZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import top.theillusivec4.curios.api.CuriosApi;

/*This is a recreation of MoreTotems Mixin but to allow Curios slots 
 * https://github.com/HyperPigeon/MoreTotems/blob/master/src/main/java/net/hyper_pigeon/moretotems/mixin/LivingEntityMixin.java*/
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public EntityType<SummonedBeeEntity> s_bee = MoreTotemsMod.SUMMONED_BEE_ENTITY;

	public MinecraftServer the_server = getServer();

	protected LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
		super(entityType_1, world_1);
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useExplosiveTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 125, 2));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 4));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 2));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);

				TntEntity tntEntity = EntityType.TNT.create(world);
				tntEntity.setFuse(5);
				tntEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
				world.spawnEntity(tntEntity);

				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useStingingTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.STINGING_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 650, 1));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 1500, 1));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);

				SummonedBeeEntity summonedBeeEntity_1 = s_bee.create(world);
				summonedBeeEntity_1.setSummoner(this);
				summonedBeeEntity_1.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
				world.spawnEntity(summonedBeeEntity_1);

				SummonedBeeEntity summonedBeeEntity_2 = s_bee.create(world);
				summonedBeeEntity_2.setSummoner(this);
				summonedBeeEntity_2.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ(), 0, 0);
				world.spawnEntity(summonedBeeEntity_2);

				SummonedBeeEntity summonedBeeEntity_3 = s_bee.create(world);
				summonedBeeEntity_3.setSummoner(this);
				summonedBeeEntity_3.refreshPositionAndAngles(this.getX() + 1, this.getY() + 1, this.getZ(), 0, 0);
				world.spawnEntity(summonedBeeEntity_3);

				SummonedBeeEntity summonedBeeEntity_4 = s_bee.create(world);
				summonedBeeEntity_4.setSummoner(this);
				summonedBeeEntity_4.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ() + 1, 0, 0);
				world.spawnEntity(summonedBeeEntity_4);

				SummonedBeeEntity summonedBeeEntity_5 = s_bee.create(world);
				summonedBeeEntity_5.setSummoner(this);
				summonedBeeEntity_5.refreshPositionAndAngles(this.getX() - 1, this.getY() + 1, this.getZ(), 0, 0);
				world.spawnEntity(summonedBeeEntity_5);

				SummonedBeeEntity summonedBeeEntity_6 = s_bee.create(world);
				summonedBeeEntity_5.setSummoner(this);
				summonedBeeEntity_5.refreshPositionAndAngles(this.getX(), this.getY() + 1, this.getZ() - 1, 0, 0);
				world.spawnEntity(summonedBeeEntity_6);

				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useTeleportingTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 17500, 5));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);

				if (livingEntity instanceof ServerPlayerEntity && !world.isClient()) {

					ServerPlayerEntity the_player = (ServerPlayerEntity) livingEntity;
					if (the_player.world.getRegistryKey() != World.OVERWORLD) {

						RegistryKey<World> registryKey = World.OVERWORLD;
						ServerWorld serverWorld2 = the_player.getServerWorld().getServer().getWorld(registryKey);

						ServerTask dimension_shift = new ServerTask((getServer().getTicks()) + 1,
								() -> the_player.moveToWorld(serverWorld2));
						the_server.send(dimension_shift);
					}

					BlockPos spawn_pointer = the_player.getSpawnPointPosition();

					System.out.println(spawn_pointer);
					if (the_player != null && spawn_pointer != null) {
						ServerTask teleport_shift = new ServerTask((getServer().getTicks()) + 1,
								() -> the_player.teleport(the_player.getServerWorld(), spawn_pointer.getX(),
										spawn_pointer.getY(), spawn_pointer.getZ(), 5.0F, 5.0F));
						the_server.send(teleport_shift);
					}

					this.world.addParticle(ParticleTypes.PORTAL, this.getParticleX(0.5D), this.getRandomBodyY() - 0.25D,
							this.getParticleZ(0.5D), (this.random.nextDouble() - 0.5D) * 2.0D,
							-this.random.nextDouble(), (this.random.nextDouble() - 0.5D) * 2.0D);
				}

				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useGhastlyTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 1325, 1));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 1525, 2));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 1225, 1));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1750, 1));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);
				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useSkeletalTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.SNIPER, 2000, 0));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 600, 0));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 1));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 250, 0));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);
				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useTentacledTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.CEPHALOPOD, 2000, 0));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 2000, 0));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 950, 1));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 2));
				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);
				ci.setReturnValue(true);
			}
		}
	}

	@Inject(method = "tryUseTotem", at = @At(value = "HEAD"), cancellable = true)
	private void useRottingTotem(DamageSource source, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (source.isOutOfWorld()) {
			ci.setReturnValue(false);
		} else {
			ItemStack stack = CuriosApi.getCuriosHelper()
					.findEquippedCurio(MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING, livingEntity)
					.map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

			if (!stack.isEmpty()) {
				stack.decrement(1);
				livingEntity.setHealth(1.0F);
				livingEntity.clearStatusEffects();
				livingEntity.addStatusEffect(new StatusEffectInstance(MoreTotemsMod.NECROSIS, 2000, 0));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 300, 2));
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 400, 0));

				SummonedZombieEntity zombie_spawn = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
				SummonedZombieEntity zombie_spawn_two = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
				SummonedZombieEntity zombie_spawn_three = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);
				SummonedZombieEntity zombie_spawn_four = MoreTotemsMod.SUMMONED_ZOMBIE_ENTITY.create(world);

				assert zombie_spawn != null;
				zombie_spawn.setSummoner(this);
				assert zombie_spawn_two != null;
				zombie_spawn_two.setSummoner(this);
				assert zombie_spawn_three != null;
				zombie_spawn_three.setSummoner(this);
				assert zombie_spawn_four != null;
				zombie_spawn_four.setSummoner(this);

				zombie_spawn.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ() + 3, 0, 0);
				zombie_spawn_two.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ() - 3, 0, 0);
				zombie_spawn_three.refreshPositionAndAngles(this.getX() - 3, this.getY(), this.getZ(), 0, 0);
				zombie_spawn_four.refreshPositionAndAngles(this.getX() + 2, this.getY(), this.getZ() + 2, 0, 0);

				world.spawnEntity(zombie_spawn);
				world.spawnEntity(zombie_spawn_two);
				world.spawnEntity(zombie_spawn_three);
				world.spawnEntity(zombie_spawn_four);

				livingEntity.world.sendEntityStatus(livingEntity, (byte) 35);
				ci.setReturnValue(true);
			}
		}
	}
}
