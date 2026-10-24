import org.shelbystewart.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Lab5Test {

    private Wizard WizBro;
    private Wizard WizGuy;
    private Archmage ArchBro;
    private Object FireBall;
    private Spell Lightning;
    private Wand ElderWand;
    @BeforeEach
    public void beforeEach(){
        WizBro = new Wizard(1, 1);
        ArchBro = new Archmage(10, 100);
        WizGuy = new Wizard(1, 1);
        FireBall = new Spell("Fireball", 20, 1);
        Lightning = new Spell("Lightning", 20, 1);
        ElderWand = new Wand(1, 1);
    }

    //Challenge 1: The Infinite Exhaustion Trap (State Transition Leak)
    @Test
    public void rest_whenExhausted_resetsStanceToIdle(){
        for(int i = 0; i < 4; i++){
            WizBro.beginChanneling();
            WizBro.spellSurge(1);
        }

        WizBro.rest();
        assertAll("The Infinite Exhaustion Trap Test",
                () -> assertEquals(600, WizBro.getManaStore()),
                () -> assertEquals( Wizard.Stance.IDLE, WizBro.getCurrentStance())
        );
    }

    @Test
    //Challenge 2: Unchannelled Surge Exception
    void surge_requires_channeling(){

        int manaBeforeSurge = WizBro.getManaStore();
        assertThatThrownBy(() -> WizBro.spellSurge(1)).isExactlyInstanceOf(InvalidStateException.class);
        assertThat(WizBro.getManaStore()).isEqualTo(manaBeforeSurge);
    }

    //Challenge 3: Mana Store Boundary Invariant Under Drain
    @ParameterizedTest
    @CsvSource
            ({
                    "Fireball, 20, 1, 599",
                    "Lightning, 20, 1, 599",
                    "Fireball, 20, 1, 599",
                    "Lightning, 20, 1, 599",
                    "Fireball, 20, 1, 599",
                    "Lightning, 20, 1, 599",
                    "Fireball, 20, 1, 599",
                    "Lightning, 20, 1, 599"
            })
    public void class_invariant_preservation(String name, int baseManaCost, int minLevelRequired, int manaInvested){
        Spell spell = new Spell(name, baseManaCost, minLevelRequired);

        WizBro.beginChanneling();
        WizBro.spellSurge(manaInvested);

        try {
            WizBro.castSpell(spell);
            fail("Mana Boundary Not Invariant Under Drain");
        }
        catch (WizardException expected){}

    }


    //Challenge 4: Liskov Substitution Principle (LSP) Invariant Violation in Archmage
    @Test
    public void manaStore_NoLowerBound(){

        ArchBro.toggleBarrier();

        ArchBro.receiveDamage(1000);

        assertTrue(ArchBro.getManaStore() >= 0);

    }

    //Challenge 5: The "Yo-Yo Effect" Cascade
    @Test
    public void spellSurge_overPower(){
        ArchBro.beginChanneling();
        int power = ArchBro.spellSurge(120);

        assertAll("The Yo-Yo Effect Cascade",
                () -> assertTrue(power > 200),
                () -> assertEquals(Wizard.Stance.CHANNELING, ArchBro.getCurrentStance()),
                () -> assertEquals(480, ArchBro.getManaStore())
        );

        for(int i = 0; i < 3; i++){
            ArchBro.beginChanneling();
            ArchBro.spellSurge(1);
        }

        assertEquals(Wizard.Stance.EXHAUSTED, ArchBro.getCurrentStance());

    }

    //Challenge 6: Dynamic Binding & Polymorphic Subclass Substitution
    @Test
    public void behavior_difference_between_wizard_and_archmage(){
        WizBro.setWand(ElderWand);
        WizBro.getCurrentStance();
        WizBro.beginChanneling();
        WizBro.rest();
        WizBro.spellSurge(1);
        WizBro.getPowerMultiplier();
        WizBro.transferMana(ArchBro, 1, 1);
        WizBro.castSpell(FireBall);
        WizBro.castSpell(Lightning);

    }

    //Challenge: 7 Homogeneous Type Parameter Swap in Inter-Object Messaging
    @Test
    public void transferMana_deductsExactAmountFromSender(){
        WizGuy.beginChanneling();
        WizGuy.spellSurge(100);

        assertEquals(500, WizGuy.getManaStore());

        WizBro.transferMana(WizGuy, 100, 1);

        assertAll("Homogeneous Type Parameter Swap in Inter-Object Messaging",
                () -> assertEquals(500, WizBro.getManaStore()),
                () -> assertEquals(600, WizGuy.getManaStore())
        );
    }

    // Challenge 8: Static Overload Resolution vs. Dynamic Polymorphic Dispatch
    @Test
    public void compiler_static_versus_runtime_dynamic(){

        try {
            int lightning_damage = WizBro.castSpell(Lightning);
            assertEquals(580, WizBro.getManaStore());
            assertEquals(40, lightning_damage);

            int fire_damage = WizBro.castSpell(FireBall);
            assertEquals(580, WizBro.getManaStore());
            assertEquals(40, fire_damage);
        }
        catch (ClassCastException | WizardException err) {
            fail("Casting or running the spell failed: " + err.getMessage());
        }

    }

}
