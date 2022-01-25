package IR;

import TEMP.*;
import MIPS.MIPSGenerator;
import TYPES.*;

public class IRcommand_New_Array extends IRcommand
{
    TEMP dst;
	TEMP size_reg;
    TYPE type;
    
    // FORMAT <dst> = new_array <size_reg>

	public IRcommand_New_Array(TEMP dst, TEMP size_reg, TYPE type)
	{
        this.dst = dst;
        this.size_reg = size_reg;
        this.type = type;
	}

	// get size_in_bytes of array from AST annotations
	public void MIPSme()
	{
		MIPSGenerator.getInstance().malloc(dst, size_reg, type.size_in_bytes);
	}

}