package AST;

import SYMBOL_TABLE.*;
import TYPES.*;
import TEMP.*;
import IR.*;

public class AST_VAR_SIMPLE extends AST_VAR
{
	// simple variable name
	public String name;
	public int line;

	// metadata for code generation
	public int offset;
	public boolean isArg;
	public boolean isLocalVar;


	// Class Constructor

	public AST_VAR_SIMPLE(String name, int line)
	{
		// SET A UNIQUE SERIAL NUMBER
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		// PRINT CORRESPONDING DERIVATION RULE
		System.out.format("====================== var -> ID( %s )\n",name);

		// COPY INPUT DATA NENBERS
		this.name = name;
		this.line = line;
	}


	// The printing message for a simple var AST node
	public void PrintMe()
	{
		// AST NODE TYPE = AST SIMPLE VAR
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		// Print to AST GRAPHIZ DOT file
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("SIMPLE\nVAR\n(%s)",name));
	}

//	public TEMP IRme()
//	{
//		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
//		IR.getInstance().Add_IRcommand(new IRcommand_Load(t,name));
//		return t;
//	}

	public BOX SemantMe() throws Exception
	{
		TYPE var_type = SYMBOL_TABLE.getInstance().find(this.name);

		if (var_type == null)
		{
			// VAR WASN'T FOUND : THROW EXCEPTION :
			String cls_name = this.getClass().getName();
			throw new Exception("SEMANTIC ERROR : " + this.line + " : " + cls_name);
		}

		// ELSE

		SYMBOL_TABLE_ENTRY entry = SYMBOL_TABLE.getInstance().find_entry(this.name);
		this.setCodeGenMetaData(entry);

		return new BOX(var_type);
	}

	public void setCodeGenMetaData(SYMBOL_TABLE_ENTRY entry)
	{
		this.offset = entry.offset;
		this.isArg = entry.isArg;
		this.isLocalVar = entry.isLocalVar;
	}

	public BOX SemantMe(TYPE_CLASS cls) throws Exception
	{
		// We won't apply this method. It is only for compilation reasons.

		return this.SemantMe();
	}
	
	public TEMP IRMe()
	{
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		
		IR.getInstance().Add_IRcommand(new IRcommand_Load(t, name));
		
		return t;
	}
}