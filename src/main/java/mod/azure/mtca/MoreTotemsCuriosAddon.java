package mod.azure.mtca;

import nerdhub.cardinal.components.api.event.ItemComponentCallbackV2;
import net.fabricmc.api.ModInitializer;
import net.hyper_pigeon.moretotems.MoreTotemsMod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosComponent;
import top.theillusivec4.curios.api.SlotTypeInfo.BuildScheme;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.component.ICurio;

public class MoreTotemsCuriosAddon implements ModInitializer {

	@Override
	public void onInitialize() {
		CuriosApi.enqueueSlotType(BuildScheme.REGISTER, SlotTypePreset.CHARM.getInfoBuilder().build());
		ItemComponentCallbackV2.event(MoreTotemsMod.EXPLOSIVE_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.STINGING_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.TELEPORTING_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.GHASTLY_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.SKELETAL_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.TENTACLED_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));
		ItemComponentCallbackV2.event(MoreTotemsMod.ROTTING_TOTEM_OF_UNDYING).register(
				((item, itemStack, componentContainer) -> componentContainer.put(CuriosComponent.ITEM, new ICurio() {
					@Override
					public boolean canRightClickEquip() {
						return true;
					}
				})));

	}
}
