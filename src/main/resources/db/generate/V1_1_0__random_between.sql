CREATE OR REPLACE FUNCTION public.random_between(low integer, high integer)
    RETURNS integer
    LANGUAGE 'plpgsql'
    
AS $$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$
