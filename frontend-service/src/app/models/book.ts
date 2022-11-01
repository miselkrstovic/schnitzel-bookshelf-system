import { Author } from "./author";

export class Book {
	public id: number = 0;
    public isbn: string = '';
    public name: string = '';
    public annotation: string = '';

    public authors?: Author[] = [];
    
	public created?: string | undefined;
	public modified?: string | undefined;

	constructor(id: number, isbn: string, name: string, annotation: string, created?: string, modified?: string) {
		this.id = id;
		this.isbn = isbn;
		this.name = name;
		this.annotation = annotation;
		this.created = created;
		this.modified = modified;
	}
}