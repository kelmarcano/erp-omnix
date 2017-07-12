select inv_numdoc as numdoc,inv_precio as totaldoc,pag_frmpag as formapago,pag_numfac as numpago,pag_monto as montorecibido,pag_cambio as cambio,0 as total from dninventario
inner join dnpagos on inv_codmae = pag_codmae WHERE inv_EMPRESA='000001' OR pag_fecha='2016-05-28' 
