	START	201
	MOVER	AREG,	='5'
	MOVEM	AREG,	X
L1	MOVER	BREG,	='2'
	ORIGIN	L1+3
	LTORG
NEXT	ADD	AREG,	='1'
	SUB	BREG,	='2'
	BC	LT,	BACK
	LTORG
BACK	EQU	L1
	ORIGIN	NEXT+5
	MULT	CREG,	='4'
	STOP
X	DS	1
	END
