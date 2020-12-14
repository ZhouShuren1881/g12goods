select
	C.id as id,
    C.username as username,
	C.create_time as create_time,
	C.password as password,
	O.id as order_id,
	O.amount as order_amount,
	D.id as detail_id,
	D.avatar as detail_avatar,
	D.addr as detail_addr
from customer C
	left outer join detail D on C.id = D.customer_id
	left outer join `orders` O on C.id = O.customer_id;