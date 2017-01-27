package soldier.units;

import soldier.core.BehaviorSoldierStd;
import soldier.core.UnitInfantry;

public class UnitBase extends UnitInfantry {

	public UnitBase(String name) {
		super(name, new BehaviorSoldierStd(30, 0));
	}

}
