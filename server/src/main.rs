use std::net::{TcpListener, TcpStream};
use std::io::{Read, Write};

fn process(mut stream: TcpStream) {
    loop {
        let mut array: [u8; 20] = [0; 20];

        stream.read(&mut array).expect("Cannot read bytes");

        array.iter().for_each(|x| print!("{} ", x));
        println!();

        let checksum = array.iter().map(|x| *x as u16 ).sum::<u16>() % 256;
        println!("Checksum: {}", checksum);

        stream.write(&[checksum as u8]).expect("Cannot write back");
    }
}

fn main() {
    let listener = TcpListener::bind("127.0.0.1:80")
        .expect("Cannot create create listener");

    for stream in listener.incoming() {
        let stream = stream.expect("Cannot get incoming stream");

        println!("Connection from {}", stream.local_addr().expect("Cannot get incoming address"));
        process(stream);

        println!();
    }
}
