if(!empty($_POST['data'])){
$data = $_POST['data'];
$fname = "./current_state.txt";

$file = fopen($fname, 'w');
fwrite($file, $data);
fclose($file);
}
