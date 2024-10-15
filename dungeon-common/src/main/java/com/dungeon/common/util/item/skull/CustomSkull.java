package com.dungeon.common.util.item.skull;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum CustomSkull {
    BARRIER("3ed1aba73f639f4bc42bd48196c715197be2712c3b962c97ebf9e9ed8efa025"),
    CHEST("d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622"),
    ACACIA_CRATE("f52d5148a107301c93e303e1f53b28635a319224a813eedc0a99cbafc0deb5c1"),
    CHEST_MINECART("4ced34211fed4010a8c85724a27fa5fb205d67684b3da517b6821279c6b65d3f"),
    COMMAND_BLOCK("1cba7277fc895bf3b673694159864b83351a4d14717e476ebda1c3bf38fcf37"),
    GOLD_BLOCK("54bf893fc6defad218f7836efefbe636f1c2cc1bb650c82fccd99f2c1ee6"),
    CACTUS("904f1a55943c594e7119e884c5da2a2bca8e7e6516a0649aa7e55658e0e9"),
    MELON("9636dee806ba47a2c40e95b57a12f37de6c2e677f2160132a07e24eeffa6"),
    NETHER_WART("111a3cec7aaf904212ccf93bb67a3caf3d649783ba90b8b60bb63c7687eb39f"),
    GREEN_CHECKMARK("a92e31ffb59c90ab08fc9dc1fe26802035a3a47c42fee63423bcdb4262ecb9b6"),
    BOOKSHELF("7dc985a7a68c574f683c0b859521feb3fc3d2ffa05fa09db0bae44b8ac29b385"),
    STONE_ARROW_LEFT("bb0f6e8af46ac6faf88914191ab66f261d6726a7999c637cf2e4159fe1fc477"),
    STONE_ARROW_RIGHT("f2f3a2dfce0c3dab7ee10db385e5229f1a39534a8ba2646178e37c4fa93b"),
    DISCORD("7873c12bffb5251a0b88d5ae75c7247cb39a75ff1a81cbe4c8a39b311ddeda"),
    MONEY_BAG("9e25dbe47667d0ce231baa223dee953bbfc9696097279d723703d2cc3397649e"),

    // COLORS
    RED_X("beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7"),
    RED("5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b"),
    RED_PLUS("ac731c3c723f67d2cfb1a1192b947086fba32aea472d347a5ed5d7642f73b"),
    RED_ARROW_UP("2d9287616343d833e9e7317159caa2cb3e59745113962c1379052ce478884fa"),
    RED_ARROW_DOWN("a3852bf616f31ed67c37de4b0baa2c5f8d8fca82e72dbcafcba66956a81c4"),
    MAGENTA_MINI_ARROW_RIGHT("a0f3103ebbcb34d73f1fe28b1fe8d8fac088aeb4e9e950bc37a132f17dc430"),
    MAGENTA_MINI_ARROW_LEFT("8f9994a477768d035e1b48dbbc0f6cddab16e8df9bd897ad353adead9226"),
    MAGENTA_ARROW_LEFT("9dbd8ec46192a955c6bd651ab4d86f9867e4d4bd99af21ca92ec9d26f86da91"),
    MAGENTA_ARROW_DOWN("7db9629cab72e021c8b561650c586b99bfdb3f6a9a962cc6e5611222d3558ef"),
    MAGENTA_ARROW_UP("cf114bec6a6fddea37d2b8767ace26b0d18283075ffd85d5687c55a7b4f1e8c"),
    MAGENTA_INFO("1fff26b2edb795d4794badd6b72f7b7cc71aa8c068768e77970dcf1daa401184"),
    YELLOW("c641682f43606c5c9ad26bc7ea8a30ee47547c9dfd3c6cda49e1c1a2816cf0ba"),
    YELLOW_INFO("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f"),
    YELLOW_PLUS("edfff1b3c5d85fe3cdd5656869baa0eade5e53aca9d561427648cc72f5e25a9"),
    GREEN("22d145c93e5eac48a661c6f27fdaff5922cf433dd627bf23eec378b9956197"),
    GREEN_ARROW_UP("b221da4418bd3bfb42eb64d2ab429c61decb8f4bf7d4cfb77a162be3dcb0b927"),
    GREEN_ARROW_DOWN("3b83bbccf4f0c86b12f6f79989d159454bf9281955d7e2411ce98c1b8aa38d8"),
    GREEN_PLUS("5ff31431d64587ff6ef98c0675810681f8c13bf96f51d9cb07ed7852b2ffd1"),
    GRAY("2a17e97037ce353f85f5c65df435d29449a88da4442e4361cf99abbe1f892fb"),
    DARK_GRAY_1("bf61269735f1e446becff25f9cb3c823679719a15f7f0fbc9a03911a692bdd"),
    DARK_GRAY_3("ceadaded81563f1c87769d6c04689dcdb9e8ca01da35281cd8fe251728d2d"),
    DARK_GRAY_5("1144c5193435199c135bd47d166ef1b4e2d3218383df9d34e3bb20d9f8e593"),
    DARK_GRAY_8("61c9c09d52debc465c32542c68be42bda6f6753fe1deba257327ac5a0c3ad"),
    DARK_GRAY_10("177a56ce415d7c30808706a94cc2bafa897cb7e486287c337a44af42b928c43"),

    // ENTITIES
    COW("5d6c6eda942f7f5f71c3161c7306f4aed307d82895f9d2b07ab4525718edc5"),
    PIG("621668ef7cb79dd9c22ce3d1f3f4cb6e2559893b6df4a469514e667c16aa4"),
    BAT("9e99deef919db66ac2bd28d6302756ccd57c7f8b12b9dca8f41c3e0a04ac1cc"),
    OCELOT("5657cd5c2989ff97570fec4ddcdc6926a68a3393250c1be1f0b114a1db1"),
    CHICKEN("1638469a599ceef7207537603248a9ab11ff591fd378bea4735b346a7fae893"),
    HORSE("7bb4b288991efb8ca0743beccef31258b31d39f24951efb1c9c18a417ba48f9"),
    MUSHROOM_COW("d0bc61b9757a7b83e03cd2507a2157913c2cf016e7c096a4d6cf1fe1b8db"),
    RABBIT("cec242e667aee44492413ef461b810cac356b74d8718e5cec1f892a6b43e5e1"),
    SHEEP("f31f9ccc6b3e32ecf13b8a11ac29cd33d18c95fc73db8a66c5d657ccb8be70"),
    SQUID("d8705624daa2956aa45956c81bab5f4fdb2c74a596051e24192039aea3a8b8"),
    VILLAGER("41b830eb4082acec836bc835e40a11282bb51193315f91184337e8d3555583"),
    CAVE_SPIDER("41645dfd77d09923107b3496e94eeb5c30329f97efc96ed76e226e98224"),
    ENDERMAN("96c0b36d53fff69a49c7d6f3932f2b0fe948e032226d5e8045ec58408a36e951"),
    IRON_GOLEM("89091d79ea0f59ef7ef94d7bba6e5f17f2f7d4572c44f90f76c4819a714"),
    SPIDER("cd541541daaff50896cd258bdbdd4cf80c3ba816735726078bfe393927e57f1"),
    WOLF("69d1d3113ec43ac2961dd59f28175fb4718873c6c448dfca8722317d67"),
    PIG_ZOMBIE("74e9c6e98582ffd8ff8feb3322cd1849c43fb16b158abb11ca7b42eda7743eb"),
    BLAZE("b78ef2e4cf2c41a2d14bfde9caff10219f5b1bf5b35a49eb51c6467882cb5f0"),
    CREEPER("f4254838c33ea227ffca223dddaabfe0b0215f70da649e944477f44370ca6952"),
    GHAST("7a8b714d32d7f6cf8b37e221b758b9c599ff76667c7cd45bbc49c5ef19858646"),
    GUARDIAN("932c24524c82ab3b3e57c2052c533f13dd8c0beb8bdd06369bb2554da86c123"),
    MAGMA_CUBE("38957d5023c937c4c41aa2412d43410bda23cf79a9f6ab36b76fef2d7c429"),
    SILVERFISH("92ec2c3cb95ab77f7a60fb4d160bced4b879329b62663d7a9860642e588ab210"),
    SKELETON("301268e9c492da1f0d88271cb492a4b302395f515a7bbf77f4a20b95fc02eb2"),
    SLIME("a20e84d32d1e9c919d3fdbb53f2b37ba274c121c57b2810e5a472f40dacf004f"),
    ZOMBIE("56fc854bb84cf4b7697297973e02b79bc10698460b51a639c60e5e417734e11"),
    WITHER("ee280cefe946911ea90e87ded1b3e18330c63a23af5129dfcfe9a8e166588041"),
    WITCH("20e13d18474fc94ed55aeb7069566e4687d773dac16f4c3f8722fc95bf9f2dfa");

    private static final String DEFAULT_SKULL_URL = "http://textures.minecraft.net/texture/";

    private static final ItemStack DEFAULT_SKULL_ITEM = new ItemStack(Material.PLAYER_HEAD);

    private static Field blockProfileField;

    private final String url;

    public static ItemStack skullFromName(String name) {
        return Bukkit.getUnsafe()
                .modifyItemStack(DEFAULT_SKULL_ITEM.clone(), "{SkullOwner:\"" + name + "\"}")
                .clone();
    }

    public static ItemStack skullFromBase64(String base64) {
        final UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe()
                .modifyItemStack(
                        DEFAULT_SKULL_ITEM.clone(),
                        "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}")
                .clone();
    }

    public static ItemStack skullFromUrl(String url) {
        if (!url.startsWith(DEFAULT_SKULL_URL)) url = DEFAULT_SKULL_URL + url;

        final String base64 = urlToBase64(url);
        return skullFromBase64(base64);
    }

    public static ItemStack getSkull(CustomSkull skull) {
        final String url = DEFAULT_SKULL_URL + skull.getUrl();

        final String base64 = urlToBase64(url);
        return skullFromBase64(base64);
    }

    public static void setBlockFromBase64(Block block, String base64) {
        setBlockToSkull(block);

        final Skull state = (Skull) block.getState();
        changeBlockState(state, base64);
    }

    public static void setBlockFromUrl(Block block, String url) {
        if (!url.startsWith(DEFAULT_SKULL_URL)) url = DEFAULT_SKULL_URL + url;

        setBlockFromBase64(block, urlToBase64(url));
    }

    public static void setBlockFromSkull(Block block, CustomSkull skull) {
        final String url = DEFAULT_SKULL_URL + skull.getUrl();

        setBlockFromBase64(block, urlToBase64(url));
    }

    private static void setBlockToSkull(Block block) {
        block.setType(Material.PLAYER_HEAD, false);
        final Skull state = (Skull) block.getState();
        state.setSkullType(SkullType.PLAYER);
        state.update(false, false);
    }

    private static GameProfile makeProfile(String b64) {
        final UUID id = new UUID(
                b64.substring(b64.length() - 20).hashCode(),
                b64.substring(b64.length() - 10).hashCode());

        final GameProfile profile = new GameProfile(id, "Player");
        profile.getProperties().put("textures", new Property("textures", b64));
        return profile;
    }

    private static String urlToBase64(String url) {
        try {
            final URI actualUrl = new URI(url);

            final String toEncode = "{\"textures\":{\"SKIN\":{\"url\":\"" + actualUrl + "\"}}}";
            return Base64.getEncoder().encodeToString(toEncode.getBytes());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeBlockState(Skull blockState, String b64) {
        try {
            if (blockProfileField == null) {
                blockProfileField = blockState.getClass().getDeclaredField("profile");
                blockProfileField.setAccessible(true);
            }

            blockProfileField.set(blockState, makeProfile(b64));
            blockState.update(false, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}