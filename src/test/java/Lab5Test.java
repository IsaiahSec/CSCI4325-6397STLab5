import org.shelbystewart.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lab5Test {

    private Wizard WizBro;
    private Wizard WizGuy;
    private Spellcaster ArchBro;
    private Object FireBall;
    @BeforeEach
    public void beforeEach(){
        WizBro = new Wizard(1, 1);
        ArchBro = new Archmage(10, 100);
        WizGuy = new Wizard(1, 1);
        FireBall = new Spell("Fireball", 20, 1);
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
    //Challenge 4: Liskov Substitution Principle (LSP) Invariant Violation in Archmage
    @Test
    public void manaStore_NoLowerBound(){

        ((Archmage) ArchBro).toggleBarrier();

        ArchBro.receiveDamage(1000);

        assertTrue(ArchBro.getManaStore() >= 0);

    }

    //Challenge 5: The "Yo-Yo Effect" Cascade
    @Test
    public void spellSurge_overPower(){
        ((Archmage) ArchBro).beginChanneling();
        int power = ((Archmage) ArchBro).spellSurge(120);

        assertAll("The Yo-Yo Effect Cascade",
                () -> assertTrue(power > 200),
                () -> assertEquals(Wizard.Stance.CHANNELING, ((Archmage) ArchBro).getCurrentStance()),
                () -> assertEquals(600, ArchBro.getManaStore())
        );

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
        int damage = WizBro.castSpell(FireBall);

        assertEquals(580, WizBro.getManaStore());
        assertEquals(40, damage);

    }

}
